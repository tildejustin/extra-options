package me.voidxwalker.options.extra;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.*;

public class ExtraOptions implements SpeedrunConfig {
    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public float distortionEffectScale = 1;

    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public float fovEffectScale = 1;

    public boolean controlBowFov = false;

    public boolean controlSubmergedFov = false;

    public static ExtraOptions config;

    {
        config = this;
    }

    @SuppressWarnings("unused")
    private Text getPercentText(float value) {
        return value == 0 ? ScreenTexts.OFF : new LiteralText((int) (value * 100) + "%");
    }

    @Override
    public String modID() {
        return "extra-options";
    }
}
