package me.voidxwalker.options.extra.optifine;

import net.minecraft.client.option.GameOption;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameOption.class)
public interface OptiFineGameOptionAccessor {
    @Dynamic
    @Invoker("<init>")
    static GameOption newOption(String s, int i, String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return null;
    }
}
