package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.option.GameOptions;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.io.IOException;
import java.util.*;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "save", at = @At("TAIL"))
    private void writeExtraOptions(CallbackInfo ci) throws IOException {
        ExtraOptions.save();
    }

    @Inject(method = "setValue", at = @At("TAIL"))
    private void setSliderOptions(GameOptions.Option option, float value, CallbackInfo ci) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            ExtraOptions.setDistortionEffectScale(value);
        }

        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            ExtraOptions.setFovEffectScale(value);
        }
    }

    @Inject(method = "getIntValue", at = @At(value = "HEAD"), cancellable = true)
    private void getSliderOptions(GameOptions.Option option, CallbackInfoReturnable<Float> cir) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getDistortionEffectScale());
        }

        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getFovEffectScale());
        }
    }

    // should be setBooleanValue
    @Inject(method = "getBooleanValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;save()V"))
    private void setBowFov(GameOptions.Option option, int integer, CallbackInfo ci) {
        if (option == ExtraOptions.DISABLE_BOW_FOV) {
            ExtraOptions.disableBowFOV = !ExtraOptions.disableBowFOV;
        }
    }

    // should be getBooleanValue
    @Inject(method = "getIntVideoOptions", at = @At("HEAD"), cancellable = true)
    private void getBowFov(GameOptions.Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ExtraOptions.DISABLE_BOW_FOV) {
            cir.setReturnValue(ExtraOptions.disableBowFOV);
        }
    }

    @Mixin(GameOptions.Option.class)
    public abstract static class OptionMixin {
        @Shadow(remap = false)
        @Final
        @Mutable
        private static GameOptions.Option[] field_1001;

        @Invoker("<init>")
        private static GameOptions.Option newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
            return null;
        }

        @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/option/GameOptions$Option;field_1001:[Lnet/minecraft/client/option/GameOptions$Option;", shift = At.Shift.AFTER))
        private static void addAccessibilityOptions(CallbackInfo ci) {
            ArrayList<GameOptions.Option> options = new ArrayList<>(Arrays.asList(field_1001));
            GameOptions.Option last = options.get(options.size() - 1);
            ExtraOptions.DISTORTION_EFFECT_SCALE = newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, /* "options.screenEffectScale" */ "Distortion Effects", true, false);
            ExtraOptions.FOV_EFFECT_SCALE = newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, /* "options.fovEffectScale" */ "FOV Effects", true, false);
            ExtraOptions.DISABLE_BOW_FOV = newOption("DISABLE_BOW_FOV", last.ordinal() + 3, /* "extra-options.disableBowFOV" */ "Disable Bow FOV", false, true);
            options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
            options.add(ExtraOptions.FOV_EFFECT_SCALE);
            options.add(ExtraOptions.DISABLE_BOW_FOV);
            field_1001 = options.toArray(new GameOptions.Option[0]);
        }
    }
}
