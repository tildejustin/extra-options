package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.class_4218;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(class_4218.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient field_20681;

    @ModifyExpressionValue(method = "method_19086", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;lastTimeInPortal:F", ordinal = 0))
    private float applyDistortionEffectScale1(float original, float tickDelta) {
        float addend = (this.field_20681.player.timeInPortal - this.field_20681.player.lastTimeInPortal) * tickDelta;
        return (float) (original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale()
                + (addend * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale() - addend));
    }
}
