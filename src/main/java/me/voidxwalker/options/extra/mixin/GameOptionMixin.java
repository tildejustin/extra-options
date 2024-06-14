package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import me.voidxwalker.options.extra.optifine.OptiFineGameOptionAccessor;
import me.voidxwalker.options.extra.vanilla.VanillaGameOptionAccessor;
import net.minecraft.client.option.GameOption;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(GameOption.class)
public abstract class GameOptionMixin {
    @Shadow(remap = false)
    @Final
    @Mutable
    private static GameOption[] field_1001;

    @SuppressWarnings({"JavaReflectionMemberAccess"})
    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/option/GameOption;field_1001:[Lnet/minecraft/client/option/GameOption;", shift = At.Shift.AFTER))
    private static void addAccessibilityOptions(CallbackInfo ci) {
        ArrayList<GameOption> options = new ArrayList<>(Arrays.asList(field_1001));
        GameOption last = options.get(options.size() - 1);

        boolean optifine = false;
        try {
            GameOption.class.getDeclaredConstructor(String.class, int.class, String.class, boolean.class, boolean.class);
        } catch (NoSuchMethodException ignored) {
            optifine = true;
        }

        if (!optifine) {
            ExtraOptions.DISTORTION_EFFECT_SCALE = VanillaGameOptionAccessor.newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, /* "options.screenEffectScale" */ "Distortion Effects", true, false);
            ExtraOptions.FOV_EFFECT_SCALE = VanillaGameOptionAccessor.newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, /* "options.fovEffectScale" */ "FOV Effects", true, false);
            ExtraOptions.CONTROL_BOW_FOV = VanillaGameOptionAccessor.newOption("CONTROL_BOW_FOV", last.ordinal() + 3, /* "extra-options.controlBowFov" */ "Control Bow FOV", false, true);
            ExtraOptions.CONTROL_SUBMERGED_FOV = VanillaGameOptionAccessor.newOption("CONTROL_SUBMERGED_FOV", last.ordinal() + 4, /* extra-options.controlSubmergedFov */ "Control Submerged FOV", false, true);
        } else {
            ExtraOptions.DISTORTION_EFFECT_SCALE = OptiFineGameOptionAccessor.newOption("DISTORTION_EFFECT_SCALE", last.ordinal() + 1, "DISTORTION_EFFECT_SCALE", last.ordinal() + 1, "Distortion Effects", true, false);
            ExtraOptions.FOV_EFFECT_SCALE = OptiFineGameOptionAccessor.newOption("FOV_EFFECT_SCALE", last.ordinal() + 2, "FOV_EFFECT_SCALE", last.ordinal() + 2, "FOV Effects", true, false);
            // yes, the + 80 is intentional.
            ExtraOptions.CONTROL_BOW_FOV = OptiFineGameOptionAccessor.newOption("CONTROL_BOW_FOV", last.ordinal() + 80, "CONTROL_BOW_FOV", last.ordinal() + 3, "Control Bow FOV", false, true);
            ExtraOptions.CONTROL_SUBMERGED_FOV = OptiFineGameOptionAccessor.newOption("CONTROL_SUBMERGED_FOV", last.ordinal() + 81, "CONTROL_SUBMERGED_FOV", last.ordinal() + 4, "Control Submerged FOV", false, true);
        }

        options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
        options.add(ExtraOptions.FOV_EFFECT_SCALE);
        options.add(ExtraOptions.CONTROL_BOW_FOV);
        options.add(ExtraOptions.CONTROL_SUBMERGED_FOV);
        field_1001 = options.toArray(new GameOption[0]);
    }
}
