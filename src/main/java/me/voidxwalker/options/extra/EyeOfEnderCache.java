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


    /**
     * remove the eye entity from the list if:
     * <ul>
     * <li>if the player world is different then eye's world (change of dimension, save and quit)
     * <li>if the eye entity has been removed from the world
     * <li>if the chunk it's in isn't loaded or not ticking entities
     * <ul>
     *     <li>because the entity age wouldn't increment and the eye stays frozen
     *
     * @param player the local ticking server player
     */
    public static void tick(ServerPlayerEntity player) {
        thrownEyes.removeIf(eye -> {
                    // very important check, if we join another world and don't do this,
                    // trying to get chunks from that entity's world will deadlock
                    if (eye.world != player.world) {
                        return true;
                    }

                    Chunk chunk = eye.world.getChunk(eye.chunkX, eye.chunkZ, ChunkStatus.FULL, false);
                    return !eye.isAlive()
                            || !(chunk instanceof WorldChunk) // implicit Chunk#isLoaded check by implicit null check
                            || !((WorldChunk) chunk).getLevelType().isAfter(ChunkHolder.LevelType.ENTITY_TICKING);
                }
        );
    }
}
