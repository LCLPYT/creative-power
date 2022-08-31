package work.lclpnet.creativepower.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class InvUtils {

    public static Optional<Block> getBlockFromPlayerHands(PlayerEntity player) {
        return getBlockFromStack(player.getMainHandStack())
                .or(() -> getBlockFromStack(player.getOffHandStack()));
    }

    public static Optional<Block> getBlockFromStack(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            return Optional.of(blockItem.getBlock());
        }
        return Optional.empty();
    }
}
