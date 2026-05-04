package work.lclpnet.creative.mixin.client;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.particle.BlockMarker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.creative.config.ConfigManager;

@Mixin(BlockMarker.class)
public class BlockMarkerMixin {

    @Unique
    private boolean adjustSize;
    @Unique
    private float size;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    public void crepow$onInit(ClientLevel level, double x, double y, double z, BlockState state, CallbackInfo ci) {
        adjustSize = ConfigManager.getInstance().getConfig().isAccurateMarkerBlocks();
        if (!adjustSize) return;

        VoxelShape shape = state.getShape(level, BlockPos.containing(x, y, z));
        if (shape.isEmpty()) {
            adjustSize = false;
            return;
        }

        AABB box = shape.bounds();
        final double xLen = box.getXsize();
        final double eps = 1e-9;

        if (Math.abs(xLen - box.getYsize()) < eps && Math.abs(xLen - box.getZsize()) < eps) {
            // cubic box; adjust size to block hit-box
            size = (float) (0.5 * xLen);
        } else {
            size = 0.5F;
        }
    }

    @Inject(
            method = "getQuadSize",
            at = @At("RETURN"),
            cancellable = true
    )
    public void crepow$manipulateSize(float a, CallbackInfoReturnable<Float> cir) {
        if (adjustSize) {
            cir.setReturnValue(size);
        }
    }
}
