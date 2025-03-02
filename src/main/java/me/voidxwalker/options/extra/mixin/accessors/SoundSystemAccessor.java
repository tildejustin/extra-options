package me.voidxwalker.options.extra.mixin.accessors;

import net.minecraft.client.sound.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundSystem.class)
public interface SoundSystemAccessor {
    @Accessor("soundEngine")
    SoundEngine getSoundEngine();
}
