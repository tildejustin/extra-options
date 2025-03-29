package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.*;
import me.voidxwalker.options.extra.*;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    // more targeted implementation that only ignores player speed's effect on fov but not bows or creative flight
    @ModifyExpressionValue(method = "getAdjustedMovementSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/living/player/InputPlayerEntity;landSpeed:F"))
    private float applyFovEffectScaleSpeedOnly(float original) {
        if (!ExtraOptions.controlBowFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 0.1f, original);
        }
        return original;
    }

    @ModifyExpressionValue(method = "getAdjustedMovementSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/InputPlayerEntity;getSpeedModifier()F"))
    private float disableModifierFOVEffect(float original) {
        if (!ExtraOptions.controlBowFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }

    // vanilla 1.16.2+ and motiono implementation
    @ModifyReturnValue(method = "getAdjustedMovementSpeed", at = @At("RETURN"))
    public float applyFovEffectScale(float original) {
        if (ExtraOptions.controlBowFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }
}
