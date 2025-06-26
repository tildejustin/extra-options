package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.EyeOfEnderCache;
import net.minecraft.entity.*;
import net.minecraft.item.EnderEyeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EnderEyeItem.class)
public abstract class EnderEyeItemMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private Entity captureThrownEye(Entity entity) {
        EyeOfEnderCache.add((EyeOfEnderEntity) entity);
        return entity;
    }
}
