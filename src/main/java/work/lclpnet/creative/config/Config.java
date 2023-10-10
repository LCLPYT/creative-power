package work.lclpnet.creative.config;

import org.json.JSONObject;
import work.lclpnet.config.json.JsonConfig;
import work.lclpnet.config.json.JsonConfigFactory;
import work.lclpnet.creative.util.ItemGroupUpdater;

public class Config implements JsonConfig {

    private boolean hideInfestedBlocks = true;
    private boolean showStructureVoids = true;
    private boolean accurateMarkerBlocks = true;
    private boolean enforceHostFullOp = false;

    public Config() {}

    public Config(JSONObject json) {
        if (json.has("inventory")) {
            JSONObject inventory = json.getJSONObject("inventory");

            if (inventory.has("hide_infested_blocks")) {
                hideInfestedBlocks = inventory.getBoolean("hide_infested_blocks");
            }
        }

        if (json.has("render")) {
            JSONObject render = json.getJSONObject("render");

            if (render.has("show_structure_voids")) {
                showStructureVoids = render.getBoolean("show_structure_voids");
            }

            if (render.has("accurate_marker_blocks")) {
                accurateMarkerBlocks = render.getBoolean("accurate_marker_blocks");
            }
        }

        if (json.has("server")) {
            JSONObject server = json.getJSONObject("server");

            if (server.has("integrated")) {
                JSONObject integrated = server.getJSONObject("integrated");

                if (integrated.has("enforce_host_full_op")) {
                    enforceHostFullOp = integrated.getBoolean("enforce_host_full_op");
                }
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        JSONObject inventory = new JSONObject();
        inventory.put("hide_infested_blocks", hideInfestedBlocks);

        json.put("inventory", inventory);

        JSONObject render = new JSONObject();
        render.put("show_structure_voids", showStructureVoids);
        render.put("accurate_marker_blocks", accurateMarkerBlocks);

        json.put("render", render);

        JSONObject server = new JSONObject();

        JSONObject integrated = new JSONObject();
        integrated.put("enforce_host_full_op", enforceHostFullOp);

        server.put("integrated", integrated);

        json.put("server", server);

        return json;
    }

    public boolean isHideInfestedBlocks() {
        return hideInfestedBlocks;
    }

    public void setHideInfestedBlocks(boolean hideInfestedBlocks) {
        if (this.hideInfestedBlocks == hideInfestedBlocks) return;

        this.hideInfestedBlocks = hideInfestedBlocks;
        ItemGroupUpdater.setDirty(true);
    }

    public boolean isShowStructureVoids() {
        return showStructureVoids;
    }

    public void setShowStructureVoids(boolean showStructureVoids) {
        this.showStructureVoids = showStructureVoids;
    }

    public boolean isAccurateMarkerBlocks() {
        return accurateMarkerBlocks;
    }

    public void setAccurateMarkerBlocks(boolean accurateMarkerBlocks) {
        this.accurateMarkerBlocks = accurateMarkerBlocks;
    }

    public boolean isEnforceHostFullOp() {
        return enforceHostFullOp;
    }

    public void setEnforceHostFullOp(boolean enforceHostFullOp) {
        this.enforceHostFullOp = enforceHostFullOp;
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
