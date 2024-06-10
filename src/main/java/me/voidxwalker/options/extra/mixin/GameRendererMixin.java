package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private Minecraft client;

    @ModifyExpressionValue(method = "setupCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ControllablePlayerEntity;field_4010:F", ordinal = 0))
    private float applyDistortionEffectScale(float original, float tickDelta, int anaglyphFilter) {
        float addend = (this.client.playerEntity.field_3997 - this.client.playerEntity.field_4010) * tickDelta;
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend);
    }
}
