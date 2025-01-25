package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.screen.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.options.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SettingsScreen.class)
public abstract class SettingsScreenMixin {
    @Shadow
    @Final
    private GameOptions options;

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIIILjava/lang/String;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=options.snooper.view")))
    private String renameSnooperOption(String name) {
        return "Accessibility Settings...";
    }

    @ModifyArg(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "intValue=104")))
    private Screen openAccessibilityScreen(@Nullable Screen screen) {
        return new AccessibilityOptionsScreen((Screen) (Object) this, this.options);
    }
}
