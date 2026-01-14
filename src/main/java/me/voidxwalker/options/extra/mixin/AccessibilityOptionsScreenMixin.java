package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.options.*;
import net.minecraft.text.MutableText;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public abstract class AccessibilityOptionsScreenMixin {
    @Mutable
    @Shadow
    @Final
    private static Option[] OPTIONS;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/options/AccessibilityOptionsScreen;OPTIONS:[Lnet/minecraft/client/options/Option;", shift = At.Shift.AFTER, opcode = Opcodes.PUTSTATIC))
    private static void addConfigurationButtons(CallbackInfo ci) {
        Option[] newOptions = Arrays.copyOf(OPTIONS, OPTIONS.length + 4);
        newOptions[newOptions.length - 4] = new DoubleOption(
                /* "options.screenEffectScale" */ "Distortion Effects", 0, 1, 0,
                options -> (double) ExtraOptions.config.distortionEffectScale,
                (options, value) -> ExtraOptions.wrapWithSave(() -> ExtraOptions.config.distortionEffectScale = value.floatValue()),
                (options, option) -> {
                    double d = option.getRatio(option.get(options));
                    MutableText text = option.getDisplayPrefix();
                    return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
                }
        );
        newOptions[newOptions.length - 3] = new DoubleOption(
                /* "options.fovEffectScale" */ "FOV Effects", 0, 1, 0,
                options -> Math.pow(ExtraOptions.config.fovEffectScale, 2),
                (options, value) -> ExtraOptions.wrapWithSave(() -> ExtraOptions.config.fovEffectScale = (float) Math.sqrt(value)),
                (options, option) -> {
                    double d = option.getRatio(option.get(options));
                    MutableText text = option.getDisplayPrefix();
                    return d == 0 ? text.append(ScreenTexts.OFF) : text.append((int) (d * 100) + "%");
                }
        );
        newOptions[newOptions.length - 2] = new BooleanOption(
                /* "extra-options.controlBowFov" */ "Control Bow FOV",
                options -> ExtraOptions.config.controlBowFov,
                (options, value) -> ExtraOptions.wrapWithSave(() -> ExtraOptions.config.controlBowFov = value)
        );
        newOptions[newOptions.length - 1] = new BooleanOption(
                /* "extra-options.controlSubmergedFov" */ "Control Submerged FOV",
                options -> ExtraOptions.config.controlSubmergedFov,
                (options, value) -> ExtraOptions.wrapWithSave(() -> ExtraOptions.config.controlSubmergedFov = value)
        );
        OPTIONS = newOptions;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"), index = 1)
    private int moveDoneButtonDown(int original) {
        return original - 144 + (OPTIONS.length + 1) / 2 * (20 + 4);
    }
}
