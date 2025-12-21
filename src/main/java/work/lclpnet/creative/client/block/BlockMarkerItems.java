package work.lclpnet.creative.client.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import work.lclpnet.creative.config.Config;
import work.lclpnet.creative.config.ConfigManager;
import work.lclpnet.creative.mixin.client.ClientLevelAccessor;

import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class BlockMarkerItems {

    public static void updateBlockMarkerItems() {
        Set<Item> old = ClientLevelAccessor.crepow$getBlockMarkerItems();
        Set<Item> markerItems = new HashSet<>(old);

        applyChanges(markerItems);

        ClientLevelAccessor.crepow$setBlockMarkerItems(markerItems);
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
