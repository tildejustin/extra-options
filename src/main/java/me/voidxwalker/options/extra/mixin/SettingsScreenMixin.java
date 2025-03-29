package me.voidxwalker.options.extra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.voidxwalker.options.extra.screen.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class SettingsScreenMixin extends Screen {
    @Shadow
    private GameOptions options;

    @SuppressWarnings("unchecked")
    @Inject(method = "init", at = @At("TAIL"))
    private void addAccessibilitySettingsButton(CallbackInfo ci) {
        // not particularly pretty but at least it's not overlapping with anything
        this.buttons.add(new ButtonWidget(106, this.width / 2 - 100, this.height / 6 + 96 - 24 - 6 + 3, "Accessibility Settings..."));
    }

    @ModifyExpressionValue(method = "buttonClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;active:Z"))
    private boolean openAccessibilityScreen(boolean original, ButtonWidget button) {
        if (original) {
            if (button.id == 106) {
                this.minecraft.openScreen(new AccessibilityOptionsScreen(this, this.options));
            }
        }
        return original;
    }
}
