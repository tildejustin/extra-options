package me.voidxwalker.options.extra.vanilla;

import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameOptions.Option.class)
public interface VanillaGameOptionAccessor {
    @Dynamic
    @Invoker("<init>")
    static GameOptions.Option newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return null;
    }
}
