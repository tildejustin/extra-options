package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private MinecraftClient client;

    @ModifyExpressionValue(method = "setupCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ControllablePlayerEntity;lastTimeInPortal:F", ordinal = 0))
    private float applyDistortionEffectScale(float original, float tickDelta, int anaglyphFilter) {
        float addend = (this.client.field_3805.timeInPortal - this.client.field_3805.lastTimeInPortal) * tickDelta;
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend);
    }
}
