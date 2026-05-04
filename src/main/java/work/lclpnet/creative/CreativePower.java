package work.lclpnet.creative;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import work.lclpnet.creative.config.ConfigManager;

import java.util.List;
import java.util.function.Predicate;

public class CreativePower implements ModInitializer {

    public static final String MOD_ID = "crepow";

    @Override
    public void onInitialize() {
        ConfigManager.getInstance().init();

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(this::modifyEntries);
    }

    private void modifyEntries(FabricCreativeModeTabOutput entries) {
        if (!ConfigManager.getInstance().getConfig().isHideInfestedBlocks()) return;

        List<ItemStack> displayStacks = entries.getDisplayStacks();
        List<ItemStack> searchStacks = entries.getSearchTabStacks();

        Predicate<ItemStack> predicate = itemStack -> itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof InfestedBlock;
        displayStacks.removeIf(predicate);
        searchStacks.removeIf(predicate);
    }
}
