package work.lclpnet.creative.mixin.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import work.lclpnet.creative.type.CreativeCooldownToggle;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin implements CreativeCooldownToggle {

    @Shadow
    private GameMode gameMode;
    @Unique
    private boolean noCreativeDelay = false;

    @ModifyConstant(
            method = {
                    "updateBlockBreakingProgress",
                    "attackBlock"
            },
            constant = @Constant(intValue = 5),
            expect = 3
    )
    public int crepow$creativeBreakCooldown(int constant) {
        if (!this.gameMode.isCreative() || !noCreativeDelay) return constant;

        return 0;
    }

    @Override
    public boolean crepow$toggleCreativeBreakCooldown() {
        return noCreativeDelay = !noCreativeDelay;
    }
}
