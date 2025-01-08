package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @ModifyExpressionValue(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;hasControlDown()Z", ordinal = 1))
    private boolean checkIfNarratorHotkeyDisabled(boolean original) {
        return original && ExtraOptions.narratorHotkey;
    }
}
