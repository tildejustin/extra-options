package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.options.AccessibilityScreen;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(AccessibilityScreen.class)
public abstract class AccessibilityScreenMixin {
    @Mutable
    @Shadow
    @Final
    private static Option[] OPTIONS;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/AccessibilityScreen;OPTIONS:[Lnet/minecraft/client/options/Option;", shift = At.Shift.AFTER))
    private static void addConfigurationButtons(CallbackInfo ci) {
        Option[] newOptions = Arrays.copyOf(OPTIONS, OPTIONS.length + 4);
        newOptions[newOptions.length - 4] = ExtraOptions.DISTORTION_EFFECT_SCALE;
        newOptions[newOptions.length - 3] = ExtraOptions.FOV_EFFECT_SCALE;
        newOptions[newOptions.length - 2] = ExtraOptions.BOW_FOV_EFFECTS;
        newOptions[newOptions.length - 1] = ExtraOptions.SUBMERGED_FOV_EFFECTS;
        OPTIONS = newOptions;
    }

    // method_25426 -> init for 20w14infinite where it's obfuscated
    @Dynamic
    @ModifyArg(method = {"init", "Lnet/minecraft/class_4189;method_25426()V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILjava/lang/String;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"), index = 1, require = 1)
    private int moveDoneButtonDown(int original) {
        return original - 144 + (OPTIONS.length + 1) / 2 * (20 + 4);
    }
}
