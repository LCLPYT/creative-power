package work.lclpnet.creative.mixin.client;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.creative.client.block.BlockMarkerItems;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void crepow$onInit(CallbackInfo ci) {
        BlockMarkerItems.updateBlockMarkerItems();
    }
}
