package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.*;
import net.minecraft.class_4218;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(class_4218.class)
public abstract class GameRendererMixin {
    @ModifyVariable(method = "method_19086", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    private float applyDistortionEffectScale(float original) {
        return (float) (original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale());
    }

    // lerping the constant from 70 to 60 in the fraction is the same as lerping the whole fraction from 1 to 60 / 70
    @ModifyConstant(method = "method_19062", constant = @Constant(doubleValue = 60))
    private double lerpFovChangeInWater(double original) {
        if (!ExtraOptions.affectWater) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 70, original);
        }
        return original;
    }
}
