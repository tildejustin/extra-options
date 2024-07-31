package me.voidxwalker.options.extra;

import net.fabricmc.loader.api.*;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.options.*;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;
import org.mcsr.speedrunapi.config.SpeedrunConfigContainer;
import org.mcsr.speedrunapi.config.api.SpeedrunConfig;
import org.mcsr.speedrunapi.config.api.annotations.Config;

import java.lang.reflect.*;

public class ExtraOptions implements SpeedrunConfig {
    @Config.Ignored
    public static DoubleOption DISTORTION_EFFECT_SCALE;
    @Config.Ignored
    public static DoubleOption FOV_EFFECT_SCALE;
    @Config.Ignored
    public static BooleanOption CONTROL_BOW_FOV;
    @Config.Ignored
    public static BooleanOption CONTROL_SUBMERGED_FOV;

    public static boolean controlBowFov = false;

    public static boolean controlSubmergedFov = false;

    @Config.Access(getter = "getDistortionEffectScale", setter = "setDistortionEffectScale")
    @Config.Numbers.Fractional.Bounds(max = 1)
    private static float distortionEffectScale = 1;

    @Config.Access(getter = "getFovEffectScale", setter = "setFovEffectScale")
    @Config.Numbers.Fractional.Bounds(max = 1)
    private static float fovEffectScale = 1;

    @Config.Ignored
    public static SpeedrunConfigContainer<ExtraOptions> container;

    @Override
    public String modID() {
        return "extra-options";
    }

    public void entrypoint() {
        try {
            Constructor<SpeedrunConfigContainer> ctor = SpeedrunConfigContainer.class.getDeclaredConstructor(SpeedrunConfig.class, ModContainer.class);
            ctor.setAccessible(true);
            container = ctor.newInstance(this, FabricLoader.getInstance().getModContainer("extra-options").get());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        DISTORTION_EFFECT_SCALE = new DoubleOption(
                /* "options.screenEffectScale" */ "Distortion Effects", 0, 1, 0,
                options -> (double) distortionEffectScale,
                (options, value) -> distortionEffectScale = value.floatValue(),
                (options, option) -> {
                    double d = option.getRatio(option.get(options));
                    MutableText text = option.getDisplayPrefix();
                    return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
                }
        );
        FOV_EFFECT_SCALE = new DoubleOption(
                /* "options.fovEffectScale" */ "FOV Effects", 0, 1, 0,
                options -> Math.pow(fovEffectScale, 2),
                (options, value) -> fovEffectScale = (float) Math.sqrt(value),
                (options, option) -> {
                    double d = option.getRatio(option.get(options));
                    MutableText text = option.getDisplayPrefix();
                    return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
                }
        );
        CONTROL_BOW_FOV = new BooleanOption(
                /* "extra-options.controlBowFov" */ "Control Bow FOV",
                options -> controlBowFov,
                (options, value) -> controlBowFov = value
        );
        CONTROL_SUBMERGED_FOV = new BooleanOption(
                /* "extra-options.controlSubmergedFov" */ "Control Submerged FOV",
                options -> controlSubmergedFov,
                (options, value) -> controlSubmergedFov = value
        );
    }

    public static float getDistortionEffectScale() {
        return distortionEffectScale;
    }

    public static void setDistortionEffectScale(float distortionEffectScale) {
        ExtraOptions.distortionEffectScale = MathHelper.clamp(distortionEffectScale, 0, 1);
    }

    public static float getFovEffectScale() {
        return fovEffectScale;
    }

    public static void setFovEffectScale(float fovEffectScale) {
        ExtraOptions.fovEffectScale = MathHelper.clamp(fovEffectScale, 0, 1);
    }
}
