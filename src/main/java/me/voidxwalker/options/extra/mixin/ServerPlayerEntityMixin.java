package me.voidxwalker.options.extra.mixin;

import me.voidxwalker.options.extra.EyeOfEnderCache;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void updateEyeOfEnderCache(CallbackInfo ci) {
        EyeOfEnderCache.tick((ServerPlayerEntity) (Object) this);
    }
}
