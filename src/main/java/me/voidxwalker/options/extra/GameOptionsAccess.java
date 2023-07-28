package me.voidxwalker.options.extra;

public interface GameOptionsAccess {
    float extra_options_getDistortionEffectScale();

    float extra_options_getFOVEffectScale();

    void extra_options_setDistortionEffectScale(float extra_options_distortionEffectScale);

    void extra_options_setFOVEffectScale(float extra_options_fovEffectScale);
}
