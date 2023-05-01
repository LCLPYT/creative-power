package work.lclpnet.creative.mixin.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @ModifyConstant(
            method = {
                    "updateBlockBreakingProgress",
                    "attackBlock"
            },
            constant = @Constant(intValue = 5),
            expect = 3
    )
    public int crepow$adjustMiningDelay(int constant) {
        return 0;
    }
}
