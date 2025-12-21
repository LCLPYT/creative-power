package work.lclpnet.creative.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ClientLevel.class)
public interface ClientLevelAccessor {

    @Mutable
    @Accessor("MARKER_PARTICLE_ITEMS")
    static Set<Item> crepow$getBlockMarkerItems() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("MARKER_PARTICLE_ITEMS")
    static void crepow$setBlockMarkerItems(Set<Item> items) {
        throw new AssertionError();
    }
}
