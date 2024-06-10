package me.voidxwalker.options.extra;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.MathHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ExtraOptions {
    private static final Path config = FabricLoader.getInstance().getConfigDir().resolve("extra-options.txt");
    public static GameOptions.Option DISTORTION_EFFECT_SCALE;
    public static GameOptions.Option FOV_EFFECT_SCALE;
    public static GameOptions.Option DISABLE_BOW_FOV;
    public static boolean disableBowFOV = false;
    private static double distortionEffectScale = 1;
    private static double fovEffectScale = 1;

    static {
        GameOptions.Option.values();
    }

    public static void init() throws IOException {
        if (!Files.exists(config)) {
            Files.createFile(config);
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
                }
            });
        }
    }

    public static void save() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(config, StandardCharsets.UTF_8)) {
            writer.write("screenEffectScale:" + distortionEffectScale + "\n");
            writer.write("fovEffectScale:" + fovEffectScale + "\n");
            writer.write("disableBowFOV:" + disableBowFOV + "\n");
        }
    }

    public static double getDistortionEffectScale() {
        return distortionEffectScale;
    }

    public static void setDistortionEffectScale(double distortionEffectScale) {
        ExtraOptions.distortionEffectScale = MathHelper.clamp(distortionEffectScale, 0, 1);
    }

    public static double getFovEffectScale() {
        return fovEffectScale;
    }

    public static void setFovEffectScale(double fovEffectScale) {
        ExtraOptions.fovEffectScale = MathHelper.clamp(fovEffectScale, 0, 1);
    }
}
