package work.lclpnet.creative.config;

import org.json.JSONObject;
import work.lclpnet.config.json.JsonConfig;
import work.lclpnet.config.json.JsonConfigFactory;
import work.lclpnet.creative.util.ItemGroupUpdater;

public class Config implements JsonConfig {

    private boolean hideInfestedBlocks = true;

    public Config() {}

    public Config(JSONObject json) {
        if (json.has("inventory")) {
            JSONObject inventory = json.getJSONObject("inventory");

            if (inventory.has("hide_infested_blocks")) {
                hideInfestedBlocks = inventory.getBoolean("hide_infested_blocks");
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        JSONObject inventory = new JSONObject();
        inventory.put("hide_infested_blocks", hideInfestedBlocks);

        json.put("inventory", inventory);

        return json;
    }

    public boolean hideInfestedBlocks() {
        return hideInfestedBlocks;
    }

    public void hideInfestedBlocks(boolean hideInfestedBlocks) {
        if (this.hideInfestedBlocks == hideInfestedBlocks) return;

        this.hideInfestedBlocks = hideInfestedBlocks;
        ItemGroupUpdater.setDirty(true);
    }

    public static final JsonConfigFactory<Config> FACTORY = new JsonConfigFactory<>() {
        @Override
        public Config createDefaultConfig() {
            return new Config();
        }

        @Override
        public Config createConfig(JSONObject json) {
            return new Config(json);
        }
    };
}
