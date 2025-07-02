package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.sugar.Local;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {
    // more targeted implementation that only ignores player speed's effect on fov but not bows or creative flight
    @ModifyExpressionValue(method = "getSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;getValue()D"))
    private double applyFovEffectScaleSpeedOnly(double original, @Local EntityAttributeInstance entityAttributeInstance) {
        if (!ExtraOptions.config.controlBowFov) {
            return MathHelper.lerp(ExtraOptions.config.fovEffectScale, entityAttributeInstance.getBaseValue(), original);
        }
        return original;
    }

    // vanilla 1.16.2+ and motiono implementation
    @ModifyReturnValue(method = "getSpeed", at = @At("RETURN"))
    public float applyFovEffectScale(float original) {
        if (ExtraOptions.config.controlBowFov) {
            return MathHelper.lerp(ExtraOptions.config.fovEffectScale, 1, original);
        }
        return original;
    }
}
