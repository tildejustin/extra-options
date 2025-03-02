package me.voidxwalker.options.extra.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.*;
import me.voidxwalker.options.extra.interfaces.SoundEngineEx;
import me.voidxwalker.options.extra.mixin.accessors.AlUtilAccessor;
import net.minecraft.client.sound.SoundEngine;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.openal.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import java.util.*;

@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin implements SoundEngineEx {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    private long devicePointer;

    @Unique
    private boolean disconnectExtensionPresent;

    @Unique
    @Nullable
    private String deviceSpecifier = findAvailableDeviceSpecifier();

    /**
     * Opens the specified audio device, or the default device if the specifier is null.
     *
     * @param deviceSpecifier The name of the audio device to open, or null to open the default device.
     * @return The handle of the opened device.
     * @throws IllegalStateException if the device cannot be opened.
     */
    @Unique
    private static long openDeviceOrFallback(@Nullable String deviceSpecifier) {
        OptionalLong optionalLong = OptionalLong.empty();
        if (deviceSpecifier != null) {
            optionalLong = tryOpenDevice(deviceSpecifier);
        }

        if (!optionalLong.isPresent()) {
            optionalLong = tryOpenDevice(findAvailableDeviceSpecifier());
        }

        if (!optionalLong.isPresent()) {
            optionalLong = tryOpenDevice(null);
        }

        if (!optionalLong.isPresent()) {
            throw new IllegalStateException("Failed to open OpenAL device");
        } else {
            return optionalLong.getAsLong();
        }
    }

    /**
     * Attempts to open the specified audio device.
     *
     * @param deviceSpecifier A string specifying the name of the audio device to open, or null to use the default device.
     * @return an {@linkplain OptionalLong} containing the handle of the opened device if successful, or empty if the device could not be opened
     */
    @SuppressWarnings({"DataFlowIssue", "ConstantValue"})
    @Unique
    private static OptionalLong tryOpenDevice(@Nullable String deviceSpecifier) {
        long l = ALC10.alcOpenDevice(deviceSpecifier);
        return l != 0L && !AlUtilAccessor.invokeCheckAlcErrors(l, "Open device") ? OptionalLong.of(l) : OptionalLong.empty();
    }

    /**
     * {@return the name of the currently selected audio device, or {@code Unknown} if it cannot be determined}
     */
    @Unique
    @Nullable
    private static String findAvailableDeviceSpecifier() {
        if (!ALC10.alcIsExtensionPresent(0L, "ALC_ENUMERATE_ALL_EXT")) {
            return null;
        } else {
            ALUtil.getStringList(0L, ALC11.ALC_ALL_DEVICES_SPECIFIER);
            return ALC10.alcGetString(0L, ALC11.ALC_DEFAULT_ALL_DEVICES_SPECIFIER);
        }
    }

    @Shadow
    public abstract void init();

    public void extra_options$init(@Nullable String deviceSpecifier) {
        this.devicePointer = openDeviceOrFallback(deviceSpecifier);
        this.init();
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;openDevice()J"))
    private long alreadyFoundDevice(Operation<Long> original) {
        return this.devicePointer;
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", remap = false))
    private void expandLogging(Logger instance, String message, Operation<Void> original) {
        LOGGER.info("OpenAL initialized on device {}", this.extra_options$getCurrentDeviceName());
        this.disconnectExtensionPresent = ALC10.alcIsExtensionPresent(this.devicePointer, "ALC_EXT_disconnect");
    }

    /**
     * Checks if the default audio device has changed since the last time this method was called.
     * <p>
     * If the default device has changed, updates the stored default device name accordingly.
     *
     * @return {@code true} if the default device has changed since the last time this method was called, {@code false} otherwise
     */
    public synchronized boolean extra_options$updateDeviceSpecifier() {
        String string = findAvailableDeviceSpecifier();
        if (Objects.equals(this.deviceSpecifier, string)) {
            return false;
        } else {
            this.deviceSpecifier = string;
            return true;
        }
    }

    public boolean extra_options$isDeviceUnavailable() {
        return this.disconnectExtensionPresent && ALC11.alcGetInteger(this.devicePointer, EXTDisconnect.ALC_CONNECTED) == 0;
    }

    /**
     * {@return the name of the default audio device, or {@code null} if it cannot be determined}
     */
    public String extra_options$getCurrentDeviceName() {
        String string = ALC10.alcGetString(this.devicePointer, ALC11.ALC_ALL_DEVICES_SPECIFIER);
        if (string == null) {
            string = ALC10.alcGetString(this.devicePointer, ALC10.ALC_DEVICE_SPECIFIER);
        }

        if (string == null) {
            string = "Unknown";
        }

        return string;
    }

    /**
     * {@return A list of strings representing the names of available sound devices, or an empty list if no devices are available.}
     */
    public List<String> extra_options$getSoundDevices() {
        List<String> list = ALUtil.getStringList(0L, ALC11.ALC_ALL_DEVICES_SPECIFIER);
        return list == null ? Collections.emptyList() : list;
    }
}
