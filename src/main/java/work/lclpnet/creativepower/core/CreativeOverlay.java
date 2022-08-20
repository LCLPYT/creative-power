package work.lclpnet.creativepower.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import work.lclpnet.creativepower.gui.OverlayScreen;

import static work.lclpnet.creativepower.CreativePower.MOD_NAME;

@Environment(EnvType.CLIENT)
public class CreativeOverlay {

    public static KeyBinding KEY_OVERLAY;
    private static final OverlayScreen overlay = new OverlayScreen();

    public static void init() {
        KEY_OVERLAY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybind.crepow.overlay",
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                MOD_NAME
        ));

        ClientTickEvents.START_CLIENT_TICK.register(CreativeOverlay::onClientTick);
    }

    public static void onClientTick(MinecraftClient mc) {
        if (KEY_OVERLAY.isPressed() && !overlay.isFocused()) {
            mc.setScreen(overlay);
            overlay.setFocused(true);
        }
    }
}
