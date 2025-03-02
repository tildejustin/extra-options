package me.voidxwalker.options.extra.mixin.accessors;

import net.minecraft.client.sound.AlUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AlUtil.class)
public interface AlUtilAccessor {
    @Invoker("checkAlcErrors")
    static boolean invokeCheckAlcErrors(long deviceHandle, String sectionName) {
        throw new IllegalStateException();
    }
}
