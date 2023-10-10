package work.lclpnet.creative.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lclpnet.config.json.ConfigHandler;
import work.lclpnet.config.json.FileConfigSerializer;
import work.lclpnet.creative.CreativePower;

import javax.annotation.Nonnull;
import java.nio.file.Path;

public class ConfigManager implements ConfigAccess {

    private final ConfigHandler<Config> configHandler;

    private ConfigManager() {
        Logger logger = LoggerFactory.getLogger(CreativePower.MOD_ID);

        var serializer = new FileConfigSerializer<>(Config.FACTORY, logger);

        Path file = FabricLoader.getInstance().getConfigDir()
                .resolve(CreativePower.MOD_ID).resolve("config.json");

        this.configHandler = new ConfigHandler<>(file, serializer, logger);
    }

    public void init() {
        configHandler.loadConfig();
    }

    public void save() {
        ConfigChangedCallback.EVENT.invoker().onUpdated(getConfig());

        // write config asynchronously
        new Thread(configHandler::writeConfig, "Config saver").start();
    }

    @Nonnull
    @Override
    public Config getConfig() {
        return configHandler.getConfig();
    }

    public static ConfigManager getInstance() {
        return ConfigManagerInstanceHolder.instance;
    }

    private static final class ConfigManagerInstanceHolder {
        private static final ConfigManager instance = new ConfigManager();
    }
}
