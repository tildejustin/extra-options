package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.option.ChatOptionsScreen;
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
    private static GameOptions.class_316[] field_2352;

    @ModifyConstant(method = "method_2224", constant = @Constant(intValue = 144))
    private int useDynamicOffset(int original) {
        return 0;
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/option/ChatOptionsScreen;field_2352:[Lnet/minecraft/client/option/GameOptions$class_316;", shift = At.Shift.AFTER))
    private static void addConfigurationButtons(CallbackInfo ci) {
        GameOptions.class_316[] newOptions = Arrays.copyOf(field_2352, field_2352.length + 3);
        newOptions[newOptions.length - 3] = ExtraOptions.DISTORTION_EFFECT_SCALE;
        newOptions[newOptions.length - 2] = ExtraOptions.FOV_EFFECT_SCALE;
        newOptions[newOptions.length - 1] = ExtraOptions.DISABLE_BOW_FOV;
        field_2352 = newOptions;
    }

    @ModifyArg(method = "method_2224", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ClickableWidget;<init>(IIILjava/lang/String;)V"), index = 2)
    private int moveDoneButtonDown(int original) {
        return original + (field_2352.length + 1) / 2 * (20 + 4);
    }

    @ModifyConstant(method = "method_0_2778", constant = @Constant(intValue = 100))
    private int tooManyOptions(int original) {
        return original + 100;
    }
}
