package work.lclpnet.creativepower.core.task;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import work.lclpnet.creativepower.core.ConditionBlockSet;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.StreamSupport;

public class FloodFillTask implements ITask<ConditionBlockSet> {

    protected final BlockView world;
    protected final BlockPos origin;
    protected final int maxDistance;

    public FloodFillTask(BlockPos origin, BlockView world, int maxDistance) {
        this.origin = Objects.requireNonNull(origin);
        this.world = Objects.requireNonNull(world);
        this.maxDistance = maxDistance;
    }

    @Override
    public ConditionBlockSet get() {
        ConditionBlockSet shape = new ConditionBlockSet(this::shouldAggregate);
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(origin);

        while (!queue.isEmpty()) {
            var pos = queue.poll();
            if (!shape.addIfValid(pos)) continue;

            StreamSupport.stream(aggregate(pos).spliterator(), true)
                    .filter(this::shouldAggregate)
                    .forEach(queue::add);
        }

        return shape;
    }

    protected Iterable<BlockPos> aggregate(BlockPos current) {
        return List.of(
                current.north(),
                current.east(),
                current.south(),
                current.west()
        );
    }

    protected boolean shouldAggregate(BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isSolid() && pos.isWithinDistance(origin, maxDistance);
    }
}
