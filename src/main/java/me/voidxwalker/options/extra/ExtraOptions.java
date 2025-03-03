package me.voidxwalker.options.extra;

import me.voidxwalker.options.extra.mixin.accessors.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.*;
import org.jetbrains.annotations.Nullable;
import org.mcsr.speedrunapi.config.SpeedrunConfigAPI;
import org.mcsr.speedrunapi.config.api.*;
import org.mcsr.speedrunapi.config.api.annotations.Config;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.*;

public class ExtraOptions implements SpeedrunConfig {
    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public static float distortionEffectScale = 1;

    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public static float fovEffectScale = 1;

    public static boolean controlBowFov = false;

    public static boolean controlSubmergedFov = false;

    public static boolean narratorHotkey = true;

    public static String audioDevice = "";

    public static boolean autoDetectDevices = false;

    @SuppressWarnings("unused")
    private Text getPercentText(float value) {
        return value == 0 ? ScreenTexts.OFF : new LiteralText((int) (value * 100) + "%");
    }

    private Text getOutputDeviceButtonMessage() {
        String openALSoftPrefix = "OpenAL Soft on ";
        if ("".equals(audioDevice)) {
            return new TranslatableText("options.audioDevice.default");
        }
        if (ExtraOptions.audioDevice.startsWith(openALSoftPrefix)) {
            return new LiteralText(ExtraOptions.audioDevice.substring(openALSoftPrefix.length()));
        }
        return new LiteralText(ExtraOptions.audioDevice);
    }

    @Override
    public @Nullable SpeedrunOption<?> parseField(Field field, SpeedrunConfig config, String... idPrefix) {
        if ("audioDevice".equals(field.getName())) {
            return new SpeedrunConfigAPI.CustomOption.Builder<String>(config, this, field, idPrefix).createWidget((option, config_, storage, field_) ->
                    new ButtonWidget(0, 0, 150, 20, getOutputDeviceButtonMessage(), button -> {
                        List<String> devices = Stream.concat(Stream.of(""),
                                (((SoundSystemAccessor) ((SoundManagerAccessor) MinecraftClient.getInstance().getSoundManager()).getSoundSystem()).getSoundEngine())
                                        .extra_options$getSoundDevices().stream()).collect(Collectors.toList());
                        ExtraOptions.audioDevice = devices.get((devices.indexOf(ExtraOptions.audioDevice) + 1) % devices.size());
                        button.setMessage(getOutputDeviceButtonMessage());
                        ((SoundManagerAccessor) MinecraftClient.getInstance().getSoundManager()).getSoundSystem().reloadSounds();
                        button.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    })).build();
        }
        return SpeedrunConfig.super.parseField(field, config, idPrefix);
    }

    @Override
    public String modID() {
        return "extra-options";
    }
}
