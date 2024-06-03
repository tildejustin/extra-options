package me.voidxwalker.options.extra;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.options.*;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ExtraOptions {
    private static final Path config = FabricLoader.getInstance().getConfigDir().resolve("extra-options.txt");
    public static DoubleOption DISTORTION_EFFECT_SCALE;
    public static DoubleOption FOV_EFFECT_SCALE;
    public static BooleanOption AFFECT_BOW;
    public static boolean affectBow = true;
    public static boolean affectWater = true;
    private static float distortionEffectScale = 1;
    private static float fovEffectScale = 1;

    public static void init() throws IOException {
        if (!Files.exists(config)) {
            Files.createFile(config);
            save();
        }
        load();

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
        AFFECT_BOW = new BooleanOption(
                /* "extraoptions.affectBow" */ "Affect Bow",
                options -> affectBow,
                (options, value) -> affectBow = value
        );
    }

    public static void load() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(config, StandardCharsets.UTF_8)) {
            reader.lines().forEach(line -> {
                String[] split = line.split(":");
                String option = split[0];
                String value = split[1];
                switch (option) {
                    case "screenEffectScale":
                        setDistortionEffectScale(Float.parseFloat(value));
                        break;
                    case "fovEffectScale":
                        setFovEffectScale(Float.parseFloat(value));
                        break;
                    case "affectBow":
                        affectBow = Boolean.parseBoolean(value);
                        break;
                    case "affectWater":
                        affectWater = Boolean.parseBoolean(value);
                        break;
                }
            });
        }
    }

    public static void save() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(config, StandardCharsets.UTF_8)) {
            writer.write("distortionEffectScale:" + distortionEffectScale + "\n");
            writer.write("fovEffectScale:" + fovEffectScale + "\n");
            writer.write("affectBow:" + affectBow + "\n");
            // semi-hidden option
            if (!affectWater) {
                writer.write("affectWater:" + affectWater + "\n");
            }
        }
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
