package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.option.GameOption;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(GameOption.class)
public abstract class GameOptionMixin {
    @Shadow(remap = false)
    @Final
    @Mutable
    private static GameOption[] field_1001;

    @Invoker("<init>")
    private static GameOption newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return null;
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/option/GameOption;field_1001:[Lnet/minecraft/client/option/GameOption;", shift = At.Shift.AFTER))
    private static void addAccessibilityOptions(CallbackInfo ci) {
        ArrayList<GameOption> options = new ArrayList<>(Arrays.asList(field_1001));
        GameOption last = options.get(options.size() - 1);
        ExtraOptions.DISTORTION_EFFECT_SCALE = newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, /* "options.screenEffectScale" */ "Distortion Effects", true, false);
        ExtraOptions.FOV_EFFECT_SCALE = newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, /* "options.fovEffectScale" */ "FOV Effects", true, false);
        ExtraOptions.CONTROL_BOW_FOV = newOption("CONTROL_BOW_FOV", last.ordinal() + 3, /* "extra-options.controlBowFov" */ "Control Bow FOV", false, true);
        ExtraOptions.CONTROL_SUBMERGED_FOV = newOption("CONTROL_SUBMERGED_FOV", last.ordinal() + 4, /* extra-options.controlSubmergedFov */ "Control Submerged FOV", false, true);
        options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
        options.add(ExtraOptions.FOV_EFFECT_SCALE);
        options.add(ExtraOptions.CONTROL_BOW_FOV);
        options.add(ExtraOptions.CONTROL_SUBMERGED_FOV);
        field_1001 = options.toArray(new GameOption[0]);
    }
}
