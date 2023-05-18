package work.lclpnet.creative.client.block;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import work.lclpnet.creative.config.Config;
import work.lclpnet.creative.config.ConfigManager;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class BlockMarkerItems {

    public static void updateBlockMarkerItems() {
        final MappingResolver mapper = FabricLoader.getInstance().getMappingResolver();
        final Class<?> owner = ClientWorld.class;

        final String fieldName = mapper.mapFieldName(
                "intermediary",
                mapper.unmapClassName("intermediary", owner.getName()),
                "field_35432",
                "Ljava/util/Set;"
        );

        final Set<Item> markerItems = new HashSet<>();

        try {
            Field field = ClientWorld.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            Set<Item> old = (Set<Item>) field.get(null);
            markerItems.addAll(old);

            applyChanges(markerItems);

            // now set the value
            field.set(null, markerItems);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not change BLOCK_MARKER_ITEMS", e);
        }
    }

    private static void applyChanges(Set<Item> markerItems) {
        final Config config = ConfigManager.getInstance().getConfig();

        if (config.isShowStructureVoids()) {
            markerItems.add(Items.STRUCTURE_VOID);
        } else {
            markerItems.remove(Items.STRUCTURE_VOID);
        }
    }
}
