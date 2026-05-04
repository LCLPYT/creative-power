package work.lclpnet.creative.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import work.lclpnet.creative.CreativePower;
import work.lclpnet.creative.client.block.BlockMarkerItems;
import work.lclpnet.creative.config.ConfigChangedCallback;
import work.lclpnet.creative.type.CreativeCooldownToggle;

public class CreativePowerClient implements ClientModInitializer {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(CreativePower.MOD_ID, "keys"));
    private static KeyMapping KEY_TOGGLE_INSTANT_MINE;

    @Override
    public void onInitializeClient() {
        KEY_TOGGLE_INSTANT_MINE = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.crepow.instant_mine",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_TOGGLE_INSTANT_MINE.consumeClick()) {
                if (client.gameMode == null || client.player == null
                        || !client.gameMode.getPlayerMode().isCreative()) continue;

                if (((CreativeCooldownToggle) client.gameMode).crepow$toggleCreativeBreakCooldown()) {
                    client.player.sendSystemMessage(Component.translatable("crepow.instant_mine.enabled").withStyle(ChatFormatting.GREEN));
                } else {
                    client.player.sendSystemMessage(Component.translatable("crepow.instant_mine.disabled").withStyle(ChatFormatting.RED));
                }
            }
        });

        ConfigChangedCallback.EVENT.register(config -> BlockMarkerItems.updateBlockMarkerItems());
    }
}
