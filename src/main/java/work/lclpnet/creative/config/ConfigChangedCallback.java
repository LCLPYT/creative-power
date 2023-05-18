package work.lclpnet.creative.config;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ConfigChangedCallback {

    Event<ConfigChangedCallback> EVENT = EventFactory.createArrayBacked(ConfigChangedCallback.class, events -> config -> {
        for (var event : events) {
            event.onUpdated(config);
        }
    });

    void onUpdated(Config config);
}
