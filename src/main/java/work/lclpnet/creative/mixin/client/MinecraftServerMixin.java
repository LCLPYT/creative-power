package work.lclpnet.creative.mixin.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.creative.config.Config;
import work.lclpnet.creative.config.ConfigManager;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow public abstract PlayerManager getPlayerManager();

    @Shadow public abstract boolean isHost(GameProfile var1);

    @Environment(EnvType.CLIENT)
    @Inject(
            method = "getPermissionLevel",
            at = @At("RETURN"),
            cancellable = true
    )
    public void crepow$modifyPermissionLevel(GameProfile profile, CallbackInfoReturnable<Integer> cir) {
        // this mixin should enforce op level 4 for the host of integrated servers
        // this is useful for example, if the host is using the essential mod to host a server
        // default behaviour is only overridden when the config option is true
        Config config = ConfigManager.getInstance().getConfig();
        if (!config.isEnforceHostFullOp()) return;

        PlayerManager playerManager = this.getPlayerManager();
        if (!playerManager.isOperator(profile)) return;

        // check if the profile belongs to the host
        if (!isHost(profile)) return;

        // this is the host profile, enforce op level 4
        cir.setReturnValue(4);
    }
}
