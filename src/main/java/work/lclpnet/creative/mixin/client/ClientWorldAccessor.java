package work.lclpnet.creative.mixin.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ClientWorld.class)
public interface ClientWorldAccessor {

    @Mutable
    @Accessor("BLOCK_MARKER_ITEMS")
    static Set<Item> crepow$getBlockMarkerItems() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("BLOCK_MARKER_ITEMS")
    static void crepow$setBlockMarkerItems(Set<Item> items) {
        throw new AssertionError();
    }
}
