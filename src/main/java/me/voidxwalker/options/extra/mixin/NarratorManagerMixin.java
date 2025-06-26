package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.text2speech.*;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.util.NarratorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NarratorManager.class)
public abstract class NarratorManagerMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/text2speech/Narrator;getNarrator()Lcom/mojang/text2speech/Narrator;"))
    private Narrator applyDisableNarratorOption(Narrator original) {
        ExtraOptions.originalNarrator = original;
        if (!ExtraOptions.config.disableNarrator) {
            return original;
        }
        return new NarratorDummy();
    }
}
