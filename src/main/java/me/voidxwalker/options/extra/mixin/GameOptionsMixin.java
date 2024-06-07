package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "write", at = @At("TAIL"))
    private void writeExtraOptions(CallbackInfo ci) throws IOException {
        ExtraOptions.save();
    }

    @Inject(method = "load", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;keysAll:[Lnet/minecraft/client/options/KeyBinding;"))
    private void loadLegacyOptions(CallbackInfo ci, @Local(ordinal = 0) String name, @Local(ordinal = 1) String value) {
        if ("extra_options_distortionEffectScale".equals(name)) {
            ExtraOptions.setDistortionEffectScale(Float.parseFloat(value));
        }
        if ("extra_options_fovEffectScale".equals(name)) {
            ExtraOptions.setFovEffectScale(Float.parseFloat(value));
        }
    }
}
