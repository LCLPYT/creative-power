package work.lclpnet.creative;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import work.lclpnet.creative.config.Config;
import work.lclpnet.creative.config.ConfigManager;

public class CreativePowerModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return CreativePowerModMenu::createConfigScreen;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigManager configManager = ConfigManager.getInstance();

        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("crepow.config.title"))
                .setSavingRunnable(configManager::save);

        Config config = configManager.getConfig();

        final var inv = builder.getOrCreateCategory(Text.translatable("crepow.config.inventory.title"));

        inv.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Text.translatable("crepow.config.inventory.infested"),
                        config.isHideInfestedBlocks()
                ).setDefaultValue(true)
                .setSaveConsumer(config::setHideInfestedBlocks)
                .build());

        inv.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Text.translatable("crepow.config.render.structure_voids"),
                        config.isShowStructureVoids()
                ).setDefaultValue(true)
                .setSaveConsumer(config::setShowStructureVoids)
                .build());

        inv.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Text.translatable("crepow.config.render.accurate_marker_blocks"),
                        config.isAccurateMarkerBlocks()
                ).setDefaultValue(false)
                .setSaveConsumer(config::setAccurateMarkerBlocks)
                .build());

        return builder.build();
    }
}
