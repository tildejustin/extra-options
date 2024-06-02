package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.*;
import com.mojang.authlib.GameProfile;
import me.voidxwalker.options.extra.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }

    // more targeted implementation that only cancels FOV affect by player speed but not Bows or Creative flight
    @ModifyExpressionValue(method = "getSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
    private double applyFovEffectScaleSpeedOnly(double original) {
        if (!ExtraOptions.affectBow && EyeOfEnderCache.shouldDisable()) {
            return this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        }
        return original;
    }

    // vanilla 1.16.2+ implementation
    @ModifyReturnValue(method = "getSpeed", at = @At("RETURN"))
    public float applyFovEffectScale(float original) {
        if (ExtraOptions.affectBow && !EyeOfEnderCache.shouldDisable()) {
            return MathHelper.lerp(ExtraOptions.getFovEffectScale(), 1, original);
        }
        return original;
    }
}
