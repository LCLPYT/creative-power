package work.lclpnet.creativepower;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class CreativePower implements ModInitializer {

    public static final String MOD_ID = "crepow";

    @Override
    public void onInitialize() {
        // common init
    }

    public static Identifier identifier(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Identifier identifier(String format, Object... substitutes) {
        return identifier(String.format(format, substitutes));
    }
}
