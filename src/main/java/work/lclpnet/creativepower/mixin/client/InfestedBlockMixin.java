package work.lclpnet.creativepower.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.block.InfestedBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import work.lclpnet.creativepower.CreativePowerConfig;

@Mixin(InfestedBlock.class)
public abstract class InfestedBlockMixin extends Block {

    public InfestedBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (!CreativePowerConfig.hideInfestedBlocks())
            super.appendStacks(group, stacks);
    }
}
