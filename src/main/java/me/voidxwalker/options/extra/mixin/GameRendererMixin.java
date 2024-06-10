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
    @Final
    private MinecraftClient client;

    @ModifyExpressionValue(method = "method_3185", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;lastNauseaStrength:F", ordinal = 0))
    private float applyDistortionEffectScale1(float original, float tickDelta, int anaglyphFilter) {
        float addend = (this.client.player.nextNauseaStrength - this.client.player.lastNauseaStrength) * tickDelta;
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend);
    }
}
