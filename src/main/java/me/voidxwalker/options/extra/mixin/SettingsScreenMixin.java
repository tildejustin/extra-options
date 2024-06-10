package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.mixin.access.SettingsScreenAccessor;
import me.voidxwalker.options.extra.screen.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SettingsScreen.class)
public abstract class SettingsScreenMixin {
    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SettingsScreen$4;<init>(Lnet/minecraft/client/gui/screen/SettingsScreen;IIIIILjava/lang/String;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=options.snooper.view")))
    private String renameSnooperOption(String name) {
        return "Accessibility Settings...";
    }

    @Mixin(targets = "net.minecraft.client.gui.screen.SettingsScreen$4")
    private static class SettingsScreen$4Mixin {
        // hidden field of anonymous classes storing the outer instance
        @Shadow
        @Final
        SettingsScreen field_20289;

        @ModifyArg(method = "method_18374", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
        private Screen openAccessibilityScreen(@Nullable Screen screen) {
            return new AccessibilityOptionsScreen(this.field_20289, ((SettingsScreenAccessor) this.field_20289).getOptions());
        }
    }
}
