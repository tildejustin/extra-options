package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.*;
import me.voidxwalker.options.extra.*;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    // more targeted implementation that only ignores player speed's effect on fov but not bows or creative flight
    @ModifyExpressionValue(method = "method_1305", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;field_3289:F"))
    private float applyFovEffectScaleSpeedOnly(float original) {
        if (ExtraOptions.bowFOVEffects) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 0.1f, original);
        }
        return original;
    }

    @ModifyExpressionValue(method = "method_1305", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;method_2646()F"))
    private float disableModifierFOVEffect(float original) {
        if (!ExtraOptions.bowFOVEffects) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }

    // vanilla 1.16.2+ and motiono implementation
    @ModifyReturnValue(method = "method_1305", at = @At("RETURN"))
    public float applyFovEffectScale(float original) {
        if (!ExtraOptions.bowFOVEffects) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }
}
