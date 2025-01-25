package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private MinecraftClient client;

    @ModifyExpressionValue(method = "setupCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;lastTimeInPortal:F", ordinal = 0))
    private float applyDistortionEffectScale(float original, float tickDelta, int anaglyphFilter) {
        float addend = (this.client.player.timeInPortal - this.client.player.lastTimeInPortal) * tickDelta;
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend);
    }

    // lerping the constant from 70 to 60 in the fraction is the same as lerping the whole fraction from 1 to 60 / 70
    @ModifyConstant(method = "getFov", constant = @Constant(floatValue = 60))
    private float lerpFovChangeInWater(float original) {
        if (ExtraOptions.controlSubmergedFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 70, original);
        }
        return original;
    }
}
