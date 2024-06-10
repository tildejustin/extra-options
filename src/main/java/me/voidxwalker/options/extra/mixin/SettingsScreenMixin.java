package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.screen.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(OptionsScreen.class)
public abstract class SettingsScreenMixin {
    @Shadow
    @Final
    private GameOptions settings;

    @ModifyArg(method = "method_2224", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ClickableWidget;<init>(IIIIILjava/lang/String;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=options.snooper.view")))
    private String renameSnooperOption(String name) {
        return "Accessibility Settings...";
    }

    @ModifyArg(method = "method_0_2778", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "intValue=104")))
    private Screen openAccessibilityScreen(@Nullable Screen screen) {
        return new AccessibilityOptionsScreen((Screen) (Object) this, this.settings);
    }
}
