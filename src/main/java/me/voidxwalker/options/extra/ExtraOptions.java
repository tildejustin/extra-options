package me.voidxwalker.options.extra;

import com.mojang.text2speech.*;
import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import me.voidxwalker.options.extra.mixin.accessor.NarratorManagerAccessor;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.*;

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

    @Config.Access(setter = "setDisableNarrator")
    public boolean disableNarrator;

    public static Narrator originalNarrator;

    public static ExtraOptions config;

    {
        config = this;
    }

    @SuppressWarnings("unused")
    private Text getPercentText(float value) {
        return value == 0 ? ScreenTexts.OFF : new LiteralText((int) (value * 100) + "%");
    }

    @SuppressWarnings("unused")
    private float getSquared() {
        return (float) Math.pow(ExtraOptions.config.fovEffectScale, 2);
    }

    @SuppressWarnings("unused")
    private void setSqrt(float fovEffectScale) {
        ExtraOptions.config.fovEffectScale = (float) Math.sqrt(fovEffectScale);
    }

    @SuppressWarnings("unused")
    private void setDisableNarrator(boolean disableNarrator) {
        this.disableNarrator = disableNarrator;
        ((NarratorManagerAccessor) NarratorManager.INSTANCE).setNarrator(disableNarrator ? new NarratorDummy() : originalNarrator);
    }

    @Override
    public String modID() {
        return "extra-options";
    }
}
