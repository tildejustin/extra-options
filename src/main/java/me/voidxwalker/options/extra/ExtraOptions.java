package me.voidxwalker.options.extra;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.GameOption;
import net.minecraft.util.math.MathHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ExtraOptions {
    private static final Path config = FabricLoader.getInstance().getConfigDir().resolve("extra-options.txt");
    public static GameOption DISTORTION_EFFECT_SCALE;
    public static GameOption FOV_EFFECT_SCALE;
    public static GameOption DISABLE_BOW_FOV;
    public static boolean disableBowFOV = false;
    public static boolean affectWater = true;
    private static float distortionEffectScale = 1;
    private static float fovEffectScale = 1;

    static {
        GameOption.values();
    }

    public static void init() throws IOException {
        if (!Files.exists(config)) {
            save();
        }
        load();
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
                    case "disableBowFOV":
                        disableBowFOV = Boolean.parseBoolean(value);
                        break;
                    case "affectWater":
                        affectWater = Boolean.parseBoolean(value);
                        break;
                }
            });
        }
    }

    public static void save() throws IOException {
        if (Files.notExists(config)) {
            Files.createFile(config);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(config, StandardCharsets.UTF_8)) {
            writer.write("screenEffectScale:" + distortionEffectScale + "\n");
            writer.write("fovEffectScale:" + fovEffectScale + "\n");
            writer.write("disableBowFOV:" + disableBowFOV + "\n");
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
