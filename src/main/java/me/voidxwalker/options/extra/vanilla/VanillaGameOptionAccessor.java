package me.voidxwalker.options.extra.vanilla;

import me.voidxwalker.options.extra.optifine.OptiFineGameOptionAccessor;
import net.minecraft.client.option.GameOption;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameOption.class)
public interface VanillaGameOptionAccessor {
    @Dynamic
    @Invoker("<init>")
    static GameOption newOption(String internalName, int ordinal, String name, boolean numeric, boolean booleanToggle) {
        return OptiFineGameOptionAccessor.newOption("", 0, internalName, ordinal, name, numeric, booleanToggle);
    }
}
