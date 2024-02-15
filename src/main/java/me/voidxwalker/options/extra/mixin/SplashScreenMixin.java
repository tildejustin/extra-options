package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.GameOptionsAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.util.math.MatrixStack;
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

    @Mutable
    @Shadow
    @Final
    private static int BRAND_ARGB;

    @Mutable
    @Shadow
    @Final
    private static int BRAND_RBG;

    @Inject(method = "render", at = @At("HEAD"))
    public void extra_options_render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BRAND_ARGB = extra_options_BRAND_ARGB.getAsInt();
        BRAND_RBG = extra_options_BRAND_ARGB.getAsInt();
    }
}
