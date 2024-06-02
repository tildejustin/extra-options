package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public abstract class AccessibilityOptionsScreenMixin {
    @Mutable
    @Shadow
    @Final
    private static Option[] OPTIONS;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/AccessibilityOptionsScreen;OPTIONS:[Lnet/minecraft/client/options/Option;", shift = At.Shift.AFTER))
    private static void addConfigurationButtons(CallbackInfo ci) {
        Option[] newOptions = Arrays.copyOf(OPTIONS, OPTIONS.length + 3);
        newOptions[newOptions.length - 3] = ExtraOptions.DISTORTION_EFFECT_SCALE;
        newOptions[newOptions.length - 2] = ExtraOptions.FOV_EFFECT_SCALE;
        newOptions[newOptions.length - 1] = ExtraOptions.AFFECT_BOW;
        OPTIONS = newOptions;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"), index = 1)
    private int moveDoneButtonDown(int original) {
        return original - 144 + (OPTIONS.length + 1) / 2 * (20 + 4);
    }
}
