package me.voidxwalker.options.extra.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import me.voidxwalker.options.extra.GameOptionsAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int ticks;

    @Redirect(method = "applyCameraTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0))
    public float extra_options_lerp(float delta, float start, float end) {
        return MathHelper.lerp(delta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength) * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale() * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale();
    }

    @Inject(method = "applyCameraTransformations", at = @At(value = "TAIL"))
    public void e(float tickDelta, CallbackInfo ci) {
        if (MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength) * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale() * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale() == 0) {
            int i = 20;
            if (this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
                i = 7;
            }

            GlStateManager.rotatef(((float) this.ticks + tickDelta) * (float) i, 0.0F, 1.0F, 1.0F);
            GlStateManager.scalef(1.0F, 1.0F, 1.0F);
            GlStateManager.rotatef(-((float) this.ticks + tickDelta) * (float) i, 0.0F, 1.0F, 1.0F);
        }
    }
}
