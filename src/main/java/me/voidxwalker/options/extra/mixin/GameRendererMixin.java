package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.*;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    // in the bytecode, var7 is actually just reassigned var4 (FSTORE 4 for both)
    // why the decompiler lies about this, I do not know
    @ModifyVariable(method = "setupCamera", at = @At(value = "STORE", ordinal = 1), ordinal = 2)
    private float applyDistortionEffectScale(float original) {
        return original * ExtraOptions.getDistortionEffectScale() * ExtraOptions.getDistortionEffectScale();
    }

    // lerping the constant from 70 to 60 in the fraction is the same as lerping the whole fraction from 1 to 60 / 70
    @ModifyConstant(method = "getFov", constant = @Constant(floatValue = 60))
    private float lerpFovChangeInWater(float original) {
        if (!ExtraOptions.affectWater) {
            return MathHelperExt.lerp(ExtraOptions.getFovEffectScale(), 70, original);
        }
        return original;
    }
}
