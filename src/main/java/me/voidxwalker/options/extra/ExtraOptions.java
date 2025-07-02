package me.voidxwalker.options.extra;

import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.minecraft.client.resource.language.I18n;

public class ExtraOptions implements SpeedrunConfig {
    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    public float distortionEffectScale = 1;

    @Config.Numbers.Fractional.Bounds(max = 1)
    @Config.Text(getter = "getPercentText")
    @Config.Access(getter = "getSquared", setter = "setSqrt")
    public float fovEffectScale = 1;

    public boolean controlBowFov = false;

    public boolean controlSubmergedFov = false;

    public static ExtraOptions config;

    {
        config = this;
    }

    @SuppressWarnings("unused")
    private String getPercentText(float value) {
        return value == 0 ? I18n.translate("options.off") : (int) (value * 100) + "%";
    }

    @SuppressWarnings("unused")
    private float getSquared() {
        return (float) Math.pow(ExtraOptions.config.fovEffectScale, 2);
    }

    @SuppressWarnings("unused")
    private void setSqrt(float fovEffectScale) {
        ExtraOptions.config.fovEffectScale = (float) Math.sqrt(fovEffectScale);
    }

    @Override
    public String modID() {
        return "extra-options";
    }
}
