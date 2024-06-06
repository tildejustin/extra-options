package me.voidxwalker.options.extra.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Pseudo
@Mixin(targets = "net.optifine.reflect.Reflector")
public abstract class ReflectorMixin {
    @Dynamic
    @ModifyConstant(method = "<clinit>", constant = @Constant(classValue = GameRenderer.class))
    private static Class<?> removeGameRendererClassUsage(Class<?> clazz) {
        return Object.class;
    }
}
