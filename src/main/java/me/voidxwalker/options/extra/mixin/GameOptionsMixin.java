package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.GameOptionsAccess;
import me.voidxwalker.options.extra.Option;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin implements GameOptionsAccess {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Unique
    public float extra_options_distortionEffectScale;

    @Unique
    public float extra_options_fovEffectScale;

    @Shadow
    private static float parseFloat(String string) {
        return 0;
    }

    @Override
    public float extra_options_getDistortionEffectScale() {
        return extra_options_distortionEffectScale;
    }

    @Override
    public float extra_options_getFOVEffectScale() {
        return extra_options_fovEffectScale;
    }

    @Override
    public void extra_options_setDistortionEffectScale(float extra_options_distortionEffectScale) {
        this.extra_options_distortionEffectScale = extra_options_distortionEffectScale;
    }

    @Override
    public void extra_options_setFOVEffectScale(float extra_options_fovEffectScale) {
        this.extra_options_fovEffectScale = extra_options_fovEffectScale;
    }

    @SuppressWarnings("DataFlowIssue")
    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getKeys()Ljava/util/Set;", ordinal = 0))
    private Set<String> extra_options_loadExtraOptions(CompoundTag instance) {
        for (String extra_options_string : instance.getKeys()) {
            String extra_options_string2 = instance.getString(extra_options_string);
            try {
                if ("extra_options_distortionEffectScale".equals(extra_options_string)) {
                    Option.extra_options_DISTORTION_EFFECT_SCALE.set((GameOptions) (Object) this, parseFloat(extra_options_string2));
                } else if ("extra_options_fovEffectScale".equals(extra_options_string)) {
                    Option.extra_options_FOV_EFFECT_SCALE.set((GameOptions) (Object) this, parseFloat(extra_options_string2));
                }
            } catch (Exception var20) {
                LOGGER.warn("(Extra Options) Failed to load options", var20);
            }
        }
        return instance.getKeys();
    }

    @Inject(method = "write", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 0))
    private void extra_options_writeExtraOptions(CallbackInfo ci, PrintWriter printWriter) {
        printWriter.println("extra_options_distortionEffectScale:" + Option.extra_options_DISTORTION_EFFECT_SCALE.get((GameOptions) (Object) this));
        printWriter.println("extra_options_fovEffectScale:" + Option.extra_options_FOV_EFFECT_SCALE.get((GameOptions) (Object) this));
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V", ordinal = 0))
    public void extra_options_init(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        this.extra_options_distortionEffectScale = 1F;
        this.extra_options_fovEffectScale = 1F;
    }
}
