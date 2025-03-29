package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.options.GameOptions;
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

    @Inject(method = "setValue(Lnet/minecraft/client/options/GameOptions$Option;F)V ", at = @At("TAIL"))
    private void setSliderOptions(GameOptions.Option option, float value, CallbackInfo ci) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            ExtraOptions.setDistortionEffectScale(value);
        }
        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            ExtraOptions.setFovEffectScale((float) Math.sqrt(value));
        }
    }

    @Inject(method = "getValueFloat", at = @At(value = "HEAD"), cancellable = true)
    private void getSliderOptions(GameOptions.Option option, CallbackInfoReturnable<Float> cir) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getDistortionEffectScale());
        }
        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            cir.setReturnValue((float) Math.pow(ExtraOptions.getFovEffectScale(), 2));
        }
    }

    @Inject(method = "setValue(Lnet/minecraft/client/options/GameOptions$Option;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;save()V"))
    private void setBooleanOptions(GameOptions.Option option, int integer, CallbackInfo ci) {
        if (option == ExtraOptions.CONTROL_BOW_FOV) {
            ExtraOptions.controlBowFov = !ExtraOptions.controlBowFov;
        }
        if (option == ExtraOptions.CONTROL_SUBMERGED_FOV) {
            ExtraOptions.controlSubmergedFov = !ExtraOptions.controlSubmergedFov;
        }
    }

    @Inject(method = "getValueBool", at = @At("HEAD"), cancellable = true)
    private void getBooleanOptions(GameOptions.Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ExtraOptions.CONTROL_BOW_FOV) {
            cir.setReturnValue(ExtraOptions.controlBowFov);
        }
        if (option == ExtraOptions.CONTROL_SUBMERGED_FOV) {
            cir.setReturnValue(ExtraOptions.controlSubmergedFov);
        }
    }
}
