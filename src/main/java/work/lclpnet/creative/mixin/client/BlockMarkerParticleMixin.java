package work.lclpnet.creative.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BlockMarkerParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.creative.config.ConfigManager;

@Mixin(BlockMarkerParticle.class)
public class BlockMarkerParticleMixin {

    @Unique
    private boolean adjustSize;
    @Unique
    private float size;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    public void crepow$onInit(ClientWorld world, double x, double y, double z, BlockState state, CallbackInfo ci) {
        adjustSize = ConfigManager.getInstance().getConfig().isAccurateMarkerBlocks();
        if (!adjustSize) return;

        VoxelShape shape = state.getOutlineShape(world, BlockPos.ofFloored(x, y, z));

        Box box = shape.getBoundingBox();
        final double xLen = box.getXLength();
        final double eps = 1e-9;

        if (Math.abs(xLen - box.getYLength()) < eps && Math.abs(xLen - box.getZLength()) < eps) {
            // cubic box; adjust size to block hit-box
            size = (float) (0.5 * xLen);
        } else {
            size = 0.5F;
        }
    }

    @Inject(
            method = "getSize",
            at = @At("RETURN"),
            cancellable = true
    )
    public void crepow$manipulateSize(float tickDelta, CallbackInfoReturnable<Float> cir) {
        if (adjustSize) {
            cir.setReturnValue(size);
        }
    }
}
