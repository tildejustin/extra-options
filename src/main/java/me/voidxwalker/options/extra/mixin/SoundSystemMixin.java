package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import me.voidxwalker.options.extra.ExtraOptions;
import me.voidxwalker.options.extra.enums.DeviceChangeStatus;
import net.minecraft.client.sound.*;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {
    @Unique
    private static final long MIN_TIME_INTERVAL_TO_RELOAD_SOUNDS = 1000L;

    @Shadow
    @Final
    private static Logger LOGGER;

    @Unique
    private final AtomicReference<DeviceChangeStatus> devicePoolState = new AtomicReference<>(DeviceChangeStatus.NO_CHANGE);

    @Shadow
    @Final
    private SoundEngine soundEngine;

    @Unique
    private long lastDeviceCheckTime;

    @Shadow
    public abstract void reloadSounds();

    @Inject(method = "tick(Z)V", at = @At("HEAD"))
    private void tickDevice(boolean bl, CallbackInfo ci) {
        if (ExtraOptions.autoDetectDevices && this.shouldReloadSounds()) {
            this.reloadSounds();
        }
    }

    @WrapOperation(method = "start", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundEngine;init()V"))
    private void initWithDevice(SoundEngine instance, Operation<Void> original) {
        String device = ExtraOptions.audioDevice;
        instance.extra_options$init("".equals(device) ? null : device);
    }

    /**
     * The audio device change is checked by this method.
     * <p>
     * If the current audio device is disconnected, an informational message is logged, and this method returns {@code true} to indicate a change is needed.
     * <p>
     * Otherwise, the elapsed time since the last device check is examined.
     * If the elapsed time is greater than or equal to 1000 milliseconds, the device check is performed.
     * <p>
     * During the device check, the current device state is compared with the preferred sound device specified in the options.
     * <ul>
     *   <li>If the preferred sound device is an empty string and the system default audio device has changed, an informational message is logged, and the device pool state is set to indicate a change has been detected.</li>
     *   <li>If the preferred sound device is not an empty string, it is checked whether the current device name is different from the preferred device name and if the preferred device is available in the list of available sound devices. </li>
     *   <li>If both conditions are true, an informational message is logged, and the device pool state is set to indicate a change has been detected.</li>
     * </ul>
     * <p>
     * Finally, the device pool state is set to indicate that the device check is complete.
     * <p>
     *
     * @return {@code true} if a change in the audio device is needed, {@code false} otherwise.
     */
    @Unique
    private boolean shouldReloadSounds() {
        if (this.soundEngine.extra_options$isDeviceUnavailable()) {
            LOGGER.info("Audio device was lost!");
            return true;
        } else {
            long l = Util.getMeasuringTimeMs();
            boolean bl = l - this.lastDeviceCheckTime >= MIN_TIME_INTERVAL_TO_RELOAD_SOUNDS;
            if (bl) {
                this.lastDeviceCheckTime = l;
                if (this.devicePoolState.compareAndSet(DeviceChangeStatus.NO_CHANGE, DeviceChangeStatus.ONGOING)) {
                    String string = ExtraOptions.audioDevice;
                    Util.getIoWorkerExecutor().execute(() -> {
                        if ("".equals(string)) {
                            if (this.soundEngine.extra_options$updateDeviceSpecifier()) {
                                LOGGER.info("System default audio device has changed!");
                                this.devicePoolState.compareAndSet(DeviceChangeStatus.ONGOING, DeviceChangeStatus.CHANGE_DETECTED);
                            }
                        } else if (!this.soundEngine.extra_options$getCurrentDeviceName().equals(string) && this.soundEngine.extra_options$getSoundDevices().contains(string)) {
                            LOGGER.info("Preferred audio device has become available!");
                            this.devicePoolState.compareAndSet(DeviceChangeStatus.ONGOING, DeviceChangeStatus.CHANGE_DETECTED);
                        }

                        this.devicePoolState.compareAndSet(DeviceChangeStatus.ONGOING, DeviceChangeStatus.NO_CHANGE);
                    });
                }
            }

            return this.devicePoolState.compareAndSet(DeviceChangeStatus.CHANGE_DETECTED, DeviceChangeStatus.NO_CHANGE);
        }
    }
}
