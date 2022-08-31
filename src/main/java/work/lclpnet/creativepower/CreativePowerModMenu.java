package work.lclpnet.creativepower;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class CreativePowerModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return CreativePowerModMenu::createConfigScreen;
    }

    public static Screen createConfigScreen(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("crepow.config.title"))
                .setSavingRunnable(Config::save);

        final var inv = builder.getOrCreateCategory(new TranslatableText("crepow.config.inventory.title"));

        inv.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        new TranslatableText("crepow.config.inventory.infested"),
                        Config.hideInfestedBlocks()
                ).setDefaultValue(true)
                .setSaveConsumer(Config::hideInfestedBlocks)
                .build());

        return builder.build();
    }
}
