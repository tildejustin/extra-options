package me.voidxwalker.options.extra;

import me.contaria.speedrunapi.config.SpeedrunConfigContainer;
import me.contaria.speedrunapi.config.api.SpeedrunConfig;
import me.contaria.speedrunapi.config.api.annotations.Config;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.*;
import org.apache.logging.log4j.*;

import java.io.IOException;

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

    public static SpeedrunConfigContainer<?> container;

    public static final Logger LOGGER = LogManager.getLogger();

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

    @Override
    public String modID() {
        return "extra-options";
    }

    @Override
    public void finishInitialization(SpeedrunConfigContainer<?> container) {
        ExtraOptions.container = container;
    }

    public static void wrapWithSave(Runnable r) {
        r.run();
        try {
            container.save();
        } catch (IOException e) {
            LOGGER.warn("Failed to save config file for {}.", container.getConfig().modID());
        }
    }
}
