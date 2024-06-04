package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.options.ChatOptionsScreen;
import net.minecraft.client.option.GameOptions;
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
    private static GameOptions.Option[] OPTIONS;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/ChatOptionsScreen;OPTIONS:[Lnet/minecraft/client/option/GameOptions$Option;", shift = At.Shift.AFTER))
    private static void addConfigurationButtons(CallbackInfo ci) {
        GameOptions.Option[] newOptions = Arrays.copyOf(OPTIONS, OPTIONS.length + 3);
        newOptions[newOptions.length - 3] = ExtraOptions.DISTORTION_EFFECT_SCALE;
        newOptions[newOptions.length - 2] = ExtraOptions.FOV_EFFECT_SCALE;
        newOptions[newOptions.length - 1] = ExtraOptions.DISABLE_BOW_FOV;
        OPTIONS = newOptions;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/options/ChatOptionsScreen$2;<init>(Lnet/minecraft/client/gui/screen/options/ChatOptionsScreen;IIILjava/lang/String;)V"), index = 3)
    private int moveDoneButtonDown(int original) {
        return original - 144 + (OPTIONS.length + 1) / 2 * (20 + 4);
    }
}
