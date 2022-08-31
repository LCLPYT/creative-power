package work.lclpnet.creativepower.core;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.io.Serial;
import java.util.HashSet;
import java.util.function.Predicate;

public class ConditionBlockSet extends HashSet<BlockPos> {

    @Serial
    private static final long serialVersionUID = 9064127235496208690L;
    private final Predicate<BlockPos> predicate;

    public ConditionBlockSet(@Nullable Predicate<BlockPos> predicate) {
        this.predicate = predicate != null ? predicate : pos -> true;
    }

    /**
     * Adds the block position to the shape, if matching the given predicate.
     * @param pos The block position.
     * @return True, if the shape did not already contain the element and if it was valid.
     */
    public boolean addIfValid(BlockPos pos) {
        if (predicate.test(pos))
            return this.add(pos);
        else
            return false;
    }
}
