package work.lclpnet.creative.client.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import work.lclpnet.creative.config.Config;
import work.lclpnet.creative.config.ConfigManager;
import work.lclpnet.creative.mixin.client.ClientWorldAccessor;

import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class BlockMarkerItems {

    public static void updateBlockMarkerItems() {
        Set<Item> old = ClientWorldAccessor.crepow$getBlockMarkerItems();
        Set<Item> markerItems = new HashSet<>(old);

        applyChanges(markerItems);

        ClientWorldAccessor.crepow$setBlockMarkerItems(markerItems);
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
