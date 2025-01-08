package me.voidxwalker.options.extra;

import org.mcsr.speedrunapi.config.api.SpeedrunConfig;
import org.mcsr.speedrunapi.config.api.annotations.Config;

public class ExtraOptions implements SpeedrunConfig {
    @Config.Numbers.Fractional.Bounds(max = 1)
    public static float distortionEffectScale = 1;

    @Config.Numbers.Fractional.Bounds(max = 1)
    public static float fovEffectScale = 1;

    public static boolean controlBowFov = false;

    public static boolean controlSubmergedFov = false;

    public static boolean narratorHotkey = true;

    // public void entrypoint() {
    //     DISTORTION_EFFECT_SCALE = new DoubleOption(
    //             /* "options.screenEffectScale" */ "Distortion Effects", 0, 1, 0,
    //             options -> (double) distortionEffectScale,
    //             (options, value) -> distortionEffectScale = value.floatValue(),
    //             (options, option) -> {
    //                 double d = option.getRatio(option.get(options));
    //                 MutableText text = option.getDisplayPrefix();
    //                 return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
    //             }
    //     );
    //     FOV_EFFECT_SCALE = new DoubleOption(
    //             /* "options.fovEffectScale" */ "FOV Effects", 0, 1, 0,
    //             options -> Math.pow(fovEffectScale, 2),
    //             (options, value) -> fovEffectScale = (float) Math.sqrt(value),
    //             (options, option) -> {
    //                 double d = option.getRatio(option.get(options));
    //                 MutableText text = option.getDisplayPrefix();
    //                 return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
    //             }
    //     );
    //     CONTROL_BOW_FOV = new BooleanOption(
    //             /* "extra-options.controlBowFov" */ "Control Bow FOV",
    //             options -> controlBowFov,
    //             (options, value) -> controlBowFov = value
    //     );
    //     CONTROL_SUBMERGED_FOV = new BooleanOption(
    //             /* "extra-options.controlSubmergedFov" */ "Control Submerged FOV",
    //             options -> controlSubmergedFov,
    //             (options, value) -> controlSubmergedFov = value
    //     );
    // }

    @Override
    public String modID() {
        return "extra-options";
    }
}
