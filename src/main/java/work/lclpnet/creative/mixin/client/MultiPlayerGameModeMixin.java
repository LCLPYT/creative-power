package work.lclpnet.creative.mixin.client;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import work.lclpnet.creative.type.CreativeCooldownToggle;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin implements CreativeCooldownToggle {

    @Shadow
    private GameType localPlayerMode;
    @Unique
    private boolean noCreativeDelay = false;

    @ModifyConstant(
            method = {
                    "continueDestroyBlock",
                    "startDestroyBlock"
            },
            constant = @Constant(intValue = 5),
            expect = 3
    )
    public int crepow$creativeBreakCooldown(int constant) {
        if (!this.localPlayerMode.isCreative() || !noCreativeDelay) return constant;

        return 0;
    }

    @Override
    public boolean crepow$toggleCreativeBreakCooldown() {
        return noCreativeDelay = !noCreativeDelay;
    }
}
