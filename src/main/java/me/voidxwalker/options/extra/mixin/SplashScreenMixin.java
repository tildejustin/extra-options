package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(SplashScreen.class)
public abstract class SplashScreenMixin extends Overlay {
    @Unique
    private static final int extra_options_MOJANG_RED = BackgroundHelper.ColorMixer.getArgb(255, 239, 50, 61);

    @Unique
    private static final int extra_options_MONOCHROME_BLACK = BackgroundHelper.ColorMixer.getArgb(255, 0, 0, 0);

    @Unique
    private static final IntSupplier extra_options_BRAND_ARGB = () -> ((GameOptionsAccess) MinecraftClient.getInstance().options).extra_options_getMonochromeLogo() ? extra_options_MONOCHROME_BLACK : extra_options_MOJANG_RED;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getScaledWidth()I", shift = At.Shift.AFTER, ordinal = 1))
    public void fill(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int i = this.client.getWindow().getScaledWidth();
        int j = this.client.getWindow().getScaledHeight();
        fill(0, 0, i, j, extra_options_BRAND_ARGB.getAsInt());
    }
}
