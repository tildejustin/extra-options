package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import me.voidxwalker.options.extra.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    // more targeted implementation that only ignores player speed's effect on fov but not bows or creative flight
    @WrapOperation(method = "method_1305", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;getValue()D"))
    private double applyFovEffectScaleSpeedOnly(EntityAttributeInstance instance, Operation<Double> operation) {
        Double original = operation.call(instance);
        if (!ExtraOptions.controlBowFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), instance.getBaseValue(), original);
        }
        return original;
    }

    // vanilla 1.16.2+ and motiono implementation
    @ModifyReturnValue(method = "method_1305", at = @At("RETURN"))
    public float applyFovEffectScale(float original) {
        if (ExtraOptions.controlBowFov) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }
}
