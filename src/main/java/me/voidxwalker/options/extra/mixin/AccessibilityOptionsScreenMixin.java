package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.Option;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AccessibilityScreen.class)
public class AccessibilityOptionsScreenMixin extends GameOptionsScreen {
    @Shadow
    @Mutable
    @Final
    private static net.minecraft.client.options.Option[] OPTIONS;

    static {
        OPTIONS = new net.minecraft.client.options.Option[]{Option.NARRATOR, Option.SUBTITLES, Option.TEXT_BACKGROUND_OPACITY, Option.TEXT_BACKGROUND, Option.CHAT_OPACITY, Option.AUTO_JUMP, Option.SNEAK_TOGGLED, Option.SPRINT_TOGGLED, Option.extra_options_DISTORTION_EFFECT_SCALE, Option.extra_options_FOV_EFFECT_SCALE, Option.extra_options_MONOCHROME_LOGO};
    }

    public AccessibilityOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/options/AccessibilityScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;"))
    public AbstractButtonWidget extra_options_addButton(AccessibilityScreen instance, AbstractButtonWidget abstractButtonWidget) {
        if (abstractButtonWidget.x == this.width / 2 - 100 && abstractButtonWidget.y == this.height / 6 + 144) {
            return this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 24 * (14) / 2, 200, 20, I18n.translate("gui.done"), (buttonWidget) -> this.minecraft.openScreen(this.parent)));
        }
        return this.addButton(abstractButtonWidget);
    }
}
