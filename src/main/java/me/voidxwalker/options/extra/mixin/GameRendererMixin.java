package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.*;
import net.minecraft.class_4218;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(class_4218.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient field_20681;

    @ModifyExpressionValue(method = "method_19086", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;lastTimeInPortal:F", ordinal = 0))
    private float applyDistortionEffectScale(float original, float tickDelta) {
        float addend = (this.field_20681.player.timeInPortal - this.field_20681.player.lastTimeInPortal) * tickDelta;
        return (float) (original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend));
    }

    // lerping the constant from 70 to 60 in the fraction is the same as lerping the whole fraction from 1 to 60 / 70
    @ModifyConstant(method = "method_19062", constant = @Constant(doubleValue = 60))
    private double lerpFovChangeInWater(double original) {
        if (!ExtraOptions.submergedFOVEffects) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 70, original);
        }
        return original;
    }
}
