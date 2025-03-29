package me.voidxwalker.options.extra.optifine;

import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameOptions.Option.class)
public interface OptiFineGameOptionAccessor {
    @Dynamic
    @Invoker("<init>")
    static GameOptions.Option newOption(String s, int i, String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return null;
    }
}
