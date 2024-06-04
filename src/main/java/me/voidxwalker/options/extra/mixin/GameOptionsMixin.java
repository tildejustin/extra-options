package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.option.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.io.IOException;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "save", at = @At("TAIL"))
    private void writeExtraOptions(CallbackInfo ci) throws IOException {
        ExtraOptions.save();
    }

    @Inject(method = "setOption(Lnet/minecraft/client/option/GameOption;F)V", at = @At("TAIL"))
    private void setSliderOptions(GameOption option, float value, CallbackInfo ci) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            ExtraOptions.setDistortionEffectScale(value);
        }

        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            ExtraOptions.setFovEffectScale(value);
        }
    }

    @Inject(method = "getFLoatOption", at = @At(value = "HEAD"), cancellable = true)
    private void getSliderOptions(GameOption option, CallbackInfoReturnable<Float> cir) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getDistortionEffectScale());
        }

        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getFovEffectScale());
        }
    }

    @Inject(method = "setOption(Lnet/minecraft/client/option/GameOption;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;save()V"))
    private void setBowFov(GameOption option, int integer, CallbackInfo ci) {
        if (option == ExtraOptions.DISABLE_BOW_FOV) {
            ExtraOptions.disableBowFOV = !ExtraOptions.disableBowFOV;
        }
    }

    @Inject(method = "gteIntOption", at = @At("HEAD"), cancellable = true)
    private void getBowFov(GameOption option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ExtraOptions.DISABLE_BOW_FOV) {
            cir.setReturnValue(ExtraOptions.disableBowFOV);
        }
    }
}
