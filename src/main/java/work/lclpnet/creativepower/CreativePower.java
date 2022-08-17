package work.lclpnet.creativepower;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativePower implements ModInitializer {

    public static final String MOD_ID = "crepow";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        CreativePowerConfig.load().whenComplete((unused, throwable) -> {
           if (throwable != null) LOGGER.error("Could not load config", throwable);
           else LOGGER.info("Config loaded successfully.");
        });
    }

    public static Identifier identifier(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Identifier identifier(String format, Object... substitutes) {
        return identifier(String.format(format, substitutes));
    }
}
