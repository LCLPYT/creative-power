package work.lclpnet.creativepower;

import net.fabricmc.loader.api.FabricLoader;
import work.lclpnet.creativepower.client.action.FloodFillAction;
import work.lclpnet.mmocontent.util.ConfigHelper;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class Config {

    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir()
            .resolve(CreativePower.MOD_ID).resolve("config.json");

    private static Config config = null;

    protected Config() {}

    /* configuration properties, will be serialized with Gson */
    public Inventory inventory = new Inventory();
    public Actions actions = new Actions();

    /* static getters and setters */
    public static boolean hideInfestedBlocks() {
        return config.inventory.hideInfestedBlocks;
    }

    public static void hideInfestedBlocks(boolean hide) {
        config.inventory.hideInfestedBlocks = hide;
    }

    public static FloodFillAction.Settings floodFillSettings() {
        return config.actions.flood_fill;
    }

    public static void floodFillSettings(FloodFillAction.Settings settings) {
        config.actions.flood_fill = settings;
    }

    /* IO logic boilerplate */
    public static CompletableFuture<Void> load() {
        return ConfigHelper.load(CONFIG_PATH, Config.class, Config::new)
                .thenAccept(conf -> config = conf);
    }

    public static CompletableFuture<Void> save() {
        return ConfigHelper.save(CONFIG_PATH, config);
    }

    public static class Inventory {
        public boolean hideInfestedBlocks = true;
    }

    public static class Actions {
        public FloodFillAction.Settings flood_fill = new FloodFillAction.Settings();
    }
}
