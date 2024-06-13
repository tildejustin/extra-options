package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @ModifyExpressionValue(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private float applyDistortionEffectScale(float original) {
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale();
    }

    // lerping the constant from 70 to 60 in the fraction is the same as lerping the whole fraction from 1 to 60 / 70
    @ModifyConstant(method = "getFov", constant = @Constant(doubleValue = 60))
    private double lerpFovChangeInWater(double original) {
        if (ExtraOptions.controlSubmergedFov) {
            return MathHelper.lerp(ExtraOptions.getFovEffectScale(), 70, original);
        }
        return original;
    }
}
