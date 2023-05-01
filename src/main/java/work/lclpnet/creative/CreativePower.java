package work.lclpnet.creative;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.InfestedBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import work.lclpnet.creative.config.ConfigManager;

import java.util.List;
import java.util.function.Predicate;

public class CreativePower implements ModInitializer {

    public static final String MOD_ID = "crepow";

    @Override
    public void onInitialize() {
        ConfigManager.getInstance().init();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(this::modifyEntries);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void modifyEntries(FabricItemGroupEntries entries) {
        if (!ConfigManager.getInstance().getConfig().hideInfestedBlocks()) return;

        List<ItemStack> displayStacks = entries.getDisplayStacks();
        List<ItemStack> searchStacks = entries.getSearchTabStacks();

        Predicate<ItemStack> predicate = itemStack -> itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof InfestedBlock;
        displayStacks.removeIf(predicate);
        searchStacks.removeIf(predicate);
    }
}
