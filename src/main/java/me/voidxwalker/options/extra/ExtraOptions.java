package me.voidxwalker.options.extra;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.*;

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

    @SuppressWarnings("unused")
    private Text getPercentText(float value) {
        return value == 0 ? ScreenTexts.OFF : new LiteralText((int) (value * 100) + "%");
    }

    @Override
    public String modID() {
        return "extra-options";
    }

    @Override
    public boolean shouldParseStaticFields() {
        return true;
    }
}
