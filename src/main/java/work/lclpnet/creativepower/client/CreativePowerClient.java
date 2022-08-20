package work.lclpnet.creativepower.client;

import net.fabricmc.api.ClientModInitializer;
import work.lclpnet.creativepower.core.CreativeOverlay;

public class CreativePowerClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CreativeOverlay.init();
    }
}
