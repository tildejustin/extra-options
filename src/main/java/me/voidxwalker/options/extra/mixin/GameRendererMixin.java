package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.GameOptionsAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F", ordinal = 0))
    public float extra_options_lerp(float delta, float start, float end) {
        // noinspection DataFlowIssue
        return MathHelper.lerp(delta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength) * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale() * ((GameOptionsAccess) this.client.options).extra_options_getDistortionEffectScale();
    }
}
