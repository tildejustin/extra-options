package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import me.voidxwalker.options.extra.optifine.OptiFineGameOptionAccessor;
import me.voidxwalker.options.extra.vanilla.VanillaGameOptionAccessor;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(GameOptions.Option.class)
public abstract class GameOptionMixin {
    // optifine forces use of $VALUES instead of f_6780349 in values(), this is a cheeky way of getting around that
    @Shadow(remap = false, aliases = "f_6780349")
    @Final
    @Mutable
    private static GameOptions.Option[] $VALUES;

    @SuppressWarnings("JavaReflectionMemberAccess")
    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void addAccessibilityOptions(CallbackInfo ci) {
        ArrayList<GameOptions.Option> options = new ArrayList<>(Arrays.asList($VALUES));
        GameOptions.Option last = options.get(options.size() - 1);

        boolean optifine = false;
        try {
            GameOptions.Option.class.getDeclaredConstructor(String.class, int.class, String.class, boolean.class, boolean.class);
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
            ExtraOptions.CONTROL_BOW_FOV = OptiFineGameOptionAccessor.newOption("CONTROL_BOW_FOV", last.ordinal() + 3, "CONTROL_BOW_FOV", last.ordinal() + 3, "Control Bow FOV", false, true);
            ExtraOptions.CONTROL_SUBMERGED_FOV = OptiFineGameOptionAccessor.newOption("CONTROL_SUBMERGED_FOV", last.ordinal() + 4, "CONTROL_SUBMERGED_FOV", last.ordinal() + 4, "Control Submerged FOV", false, true);
        }
        options.add(ExtraOptions.DISTORTION_EFFECT_SCALE);
        options.add(ExtraOptions.FOV_EFFECT_SCALE);
        options.add(ExtraOptions.CONTROL_BOW_FOV);
        options.add(ExtraOptions.CONTROL_SUBMERGED_FOV);
        $VALUES = options.toArray(new GameOptions.Option[0]);
    }
}
