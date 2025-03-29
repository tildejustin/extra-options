package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.options.GameOptions;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(GameOptions.Option.class)
public abstract class GameOptionMixin {
    @Shadow(remap = false)
    @Final
    @Mutable
    private static GameOptions.Option[] f_6780349;

    @Invoker("<init>")
    private static GameOptions.Option newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/options/GameOptions$Option;f_6780349:[Lnet/minecraft/client/options/GameOptions$Option;", shift = At.Shift.AFTER))
    private static void addAccessibilityOptions(CallbackInfo ci) {
        ArrayList<GameOptions.Option> options = new ArrayList<>(Arrays.asList(f_6780349));
        GameOptions.Option last = options.get(options.size() - 1);
        ExtraOptions.DISTORTION_EFFECT_SCALE = newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, /* "options.screenEffectScale" */ "Distortion Effects", true, false);
        ExtraOptions.FOV_EFFECT_SCALE = newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, /* "options.fovEffectScale" */ "FOV Effects", true, false);
        ExtraOptions.CONTROL_BOW_FOV = newOption("CONTROL_BOW_FOV", last.ordinal() + 3, /* "extra-options.controlBowFov" */ "Control Bow FOV", false, true);
        ExtraOptions.CONTROL_SUBMERGED_FOV = newOption("CONTROL_SUBMERGED_FOV", last.ordinal() + 4, /* extra-options.controlSubmergedFov */ "Control Submerged FOV", false, true);
        options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
        options.add(ExtraOptions.FOV_EFFECT_SCALE);
        options.add(ExtraOptions.CONTROL_BOW_FOV);
        options.add(ExtraOptions.CONTROL_SUBMERGED_FOV);
        f_6780349 = options.toArray(new GameOptions.Option[0]);
    }
}
