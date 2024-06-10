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
    @Inject(method = "write", at = @At("TAIL"))
    private void writeExtraOptions(CallbackInfo ci) throws IOException {
        ExtraOptions.save();
    }

    @Inject(method = "method_1625", at = @At("TAIL"))
    private void setSliderOptions(GameOptions.class_316 option, float value, CallbackInfo ci) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            ExtraOptions.setDistortionEffectScale(value);
        }
        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            ExtraOptions.setFovEffectScale((float) Math.sqrt(value));
        }
    }

    @Inject(method = "method_1637", at = @At(value = "HEAD"), cancellable = true)
    private void getSliderOptions(GameOptions.class_316 option, CallbackInfoReturnable<Float> cir) {
        if (option == ExtraOptions.DISTORTION_EFFECT_SCALE) {
            cir.setReturnValue(ExtraOptions.getDistortionEffectScale());
        }
        if (option == ExtraOptions.FOV_EFFECT_SCALE) {
            cir.setReturnValue((float) Math.pow(ExtraOptions.getFovEffectScale(), 2));
        }
    }

    // should be setBooleanValue
    @Inject(method = "method_1629", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;write()V"))
    private void setBooleanOptions(GameOptions.class_316 option, int integer, CallbackInfo ci) {
        if (option == ExtraOptions.BOW_FOV_EFFECTS) {
            ExtraOptions.bowFOVEffects = !ExtraOptions.bowFOVEffects;
        }
        if (option == ExtraOptions.SUBMERGED_FOV_EFFECTS) {
            ExtraOptions.submergedFOVEffects = !ExtraOptions.submergedFOVEffects;
        }
    }

    // should be getBooleanValue
    @Inject(method = "method_1628", at = @At("HEAD"), cancellable = true)
    private void getBooleanOptions(GameOptions.class_316 option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ExtraOptions.BOW_FOV_EFFECTS) {
            cir.setReturnValue(ExtraOptions.bowFOVEffects);
        }
        if (option == ExtraOptions.SUBMERGED_FOV_EFFECTS) {
            cir.setReturnValue(ExtraOptions.submergedFOVEffects);
        }
    }

    @Mixin(GameOptions.class_316.class)
    public abstract static class OptionMixin {
        @Shadow(remap = false)
        @Final
        @Mutable
        private static GameOptions.class_316[] field_1918;

        @Invoker("<init>")
        private static GameOptions.class_316 newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
            return null;
        }

        @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/option/GameOptions$class_316;field_1918:[Lnet/minecraft/client/option/GameOptions$class_316;", shift = At.Shift.AFTER))
        private static void addAccessibilityOptions(CallbackInfo ci) {
            ArrayList<GameOptions.class_316> options = new ArrayList<>(Arrays.asList(field_1918));
            GameOptions.class_316 last = options.get(options.size() - 1);
            ExtraOptions.DISTORTION_EFFECT_SCALE = newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, /* "options.screenEffectScale" */ "Distortion Effects", true, false);
            ExtraOptions.FOV_EFFECT_SCALE = newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, /* "options.fovEffectScale" */ "FOV Effects", true, false);
            ExtraOptions.BOW_FOV_EFFECTS = newOption("BOW_FOV_EFFECTS", last.ordinal() + 3, /* "extra-options.bowFOVEffects" */ "Bow FOV Effects", false, true);
            ExtraOptions.SUBMERGED_FOV_EFFECTS = newOption("SUBMERGED_FOV_EFFECTS", last.ordinal() + 4, /* "extra-options.submergedFOVEffects" */ "Submerged FOV Effects", false, true);
            options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
            options.add(ExtraOptions.FOV_EFFECT_SCALE);
            options.add(ExtraOptions.BOW_FOV_EFFECTS);
            options.add(ExtraOptions.SUBMERGED_FOV_EFFECTS);
            field_1918 = options.toArray(new GameOptions.class_316[0]);
        }
    }
}
