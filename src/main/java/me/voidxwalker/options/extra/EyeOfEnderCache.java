package me.voidxwalker.options.extra;

import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.world.chunk.*;

import java.util.*;

public class EyeOfEnderCache {
    private static final List<EyeOfEnderEntity> thrownEyes = Collections.synchronizedList(new ArrayList<>());

    public static boolean shouldDisable() {
        return !thrownEyes.isEmpty();
    }

    public static void add(EyeOfEnderEntity eye) {
        thrownEyes.add(eye);
    }

    // public static void clear() {
    //     thrownEyes.clear();
    // }

    public static void tick(ServerPlayerEntity player) {
        // remove the eye entity from the list if it's been removed
        // or the ticking world is a different instance (change of dimension, save and quit)
        thrownEyes.removeIf(eye -> {
                    // very important check, if we join another world and don't do this check,
                    // trying to get chunks from that entity's world will deadlock
                    if (eye.world != player.world) {
                        return true;
                    }

                    Chunk chunk = eye.world.getChunk(eye.chunkX, eye.chunkZ, ChunkStatus.FULL, false);
                    return !eye.isAlive()
                            || !(chunk instanceof WorldChunk) // implicit isLoaded check by implicit null check
                            || !((WorldChunk) chunk).getLevelType().isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
                }
        );
    }
}
