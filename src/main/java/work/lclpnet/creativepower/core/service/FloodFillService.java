package work.lclpnet.creativepower.core.service;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import work.lclpnet.creativepower.core.task.FloodFillTask;

import java.util.concurrent.CompletableFuture;

public class FloodFillService {

    public static void fill(BlockPos origin, BlockView world, int maxDistance, BlockState state) {
        var shape = new FloodFillTask(origin, world, maxDistance).get();

    }
}
