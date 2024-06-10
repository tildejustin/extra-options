package me.voidxwalker.options.extra.mixin.access;

import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SettingsScreen.class)
public interface SettingsScreenAccessor {
    @Accessor("options")
    GameOptions getOptions();
}
