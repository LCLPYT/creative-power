package work.lclpnet.creativepower;

import net.fabricmc.loader.api.FabricLoader;
import work.lclpnet.mmocontent.util.ConfigHelper;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class CreativePowerConfig {

    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir()
            .resolve(CreativePower.MOD_ID).resolve("config.json");

    private static CreativePowerConfig config = null;

    protected CreativePowerConfig() {}

    /* configuration properties, will be serialized with Gson */
    public Inventory inventory = new Inventory();

    /* static getters and setters */
    public static boolean hideInfestedBlocks() {
        return config.inventory.hideInfestedBlocks;
    }

    public static void hideInfestedBlocks(boolean hide) {
        config.inventory.hideInfestedBlocks = hide;
    }

    /* IO logic boilerplate */
    public static CompletableFuture<Void> load() {
        return ConfigHelper.load(CONFIG_PATH, CreativePowerConfig.class, CreativePowerConfig::new)
                .thenAccept(conf -> config = conf);
    }

    public static CompletableFuture<Void> save() {
        return ConfigHelper.save(CONFIG_PATH, config);
    }

    public static class Inventory {
        public boolean hideInfestedBlocks = true;
    }
}
