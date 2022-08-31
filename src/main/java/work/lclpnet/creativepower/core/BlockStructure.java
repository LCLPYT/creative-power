package work.lclpnet.creativepower.core;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.ModifiableWorld;
import work.lclpnet.creativepower.GenericStructure;

import java.io.Serial;

public class BlockStructure extends GenericStructure<BlockState> {

    @Serial
    private static final long serialVersionUID = -8811001815493540329L;

    public void placeInWorld(ModifiableWorld w) {
        keySet().stream()
                // group blocks by region file and by chunk to improve region / chunk io performance
                .sorted(POS_COMPARATOR)
                // place each block, in order
                .forEachOrdered(pos -> {
                    var state = get(pos);
                    w.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
                });
    }
}
