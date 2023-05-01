package work.lclpnet.creative.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import work.lclpnet.creative.type.CreativeCooldownToggle;

public class CreativePowerClient implements ClientModInitializer {

    private static KeyBinding KEY_TOGGLE_INSTANT_MINE;

    @Override
    public void onInitializeClient() {
        KEY_TOGGLE_INSTANT_MINE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.crepow.instant_mine",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "category.crepow.title"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KEY_TOGGLE_INSTANT_MINE.wasPressed()) {
                if (client.interactionManager == null || client.player == null
                        || !client.interactionManager.getCurrentGameMode().isCreative()) continue;

                if (((CreativeCooldownToggle) client.interactionManager).crepow$toggleCreativeBreakCooldown()) {
                    client.player.sendMessage(Text.translatable("crepow.instant_mine.enabled").formatted(Formatting.GREEN));
                } else {
                    client.player.sendMessage(Text.translatable("crepow.instant_mine.disabled").formatted(Formatting.RED));
                }
            }
        });
    }
}
