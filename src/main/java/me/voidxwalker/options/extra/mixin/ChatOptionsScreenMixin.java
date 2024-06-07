package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.options.ChatOptionsScreen;
import net.minecraft.client.option.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// adding accessibility options to chat screen
@Mixin(ChatOptionsScreen.class)
public abstract class ChatOptionsScreenMixin {
    @Mutable
    @Shadow
    @Final
    private static GameOption[] field_1062;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/ChatOptionsScreen;field_1062:[Lnet/minecraft/client/option/GameOption;", shift = At.Shift.AFTER))
    private static void addConfigurationButtons(CallbackInfo ci) {
        GameOption[] newOptions = Arrays.copyOf(field_1062, field_1062.length + 3);
        newOptions[newOptions.length - 3] = ExtraOptions.DISTORTION_EFFECT_SCALE;
        newOptions[newOptions.length - 2] = ExtraOptions.FOV_EFFECT_SCALE;
        newOptions[newOptions.length - 1] = ExtraOptions.DISABLE_BOW_FOV;
        field_1062 = newOptions;
    }

    @ModifyArg(method = "method_21947", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIILjava/lang/String;)V"), index = 2)
    private int moveDoneButtonDown(int original) {
        return original + 24;
    }
}
