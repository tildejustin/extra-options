package me.voidxwalker.options.extra;

public class MathHelperExt {
    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }
}
