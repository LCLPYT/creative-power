package work.lclpnet.creative;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
                .setTitle(Component.translatable("crepow.config.title"))
                .setSavingRunnable(configManager::save);

        Config config = configManager.getConfig();

        ConfigCategory inv = builder.getOrCreateCategory(Component.translatable("crepow.config.inventory.title"));

        inv.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Component.translatable("crepow.config.inventory.infested"),
                        config.isHideInfestedBlocks()
                ).setDefaultValue(true)
                .setSaveConsumer(config::setHideInfestedBlocks)
                .build());

        ConfigCategory render = builder.getOrCreateCategory(Component.translatable("crepow.config.render.title"));

        render.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Component.translatable("crepow.config.render.structure_voids"),
                        config.isShowStructureVoids()
                ).setDefaultValue(true)
                .setSaveConsumer(config::setShowStructureVoids)
                .build());

        render.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Component.translatable("crepow.config.render.accurate_marker_blocks"),
                        config.isAccurateMarkerBlocks()
                ).setDefaultValue(true)
                .setSaveConsumer(config::setAccurateMarkerBlocks)
                .build());

        ConfigCategory server = builder.getOrCreateCategory(Component.translatable("crepow.config.server.title"));

        server.addEntry(builder.entryBuilder()
                .startBooleanToggle(
                        Component.translatable("crepow.config.server.enforce_host_full_op"),
                        config.isEnforceHostFullOp()
                ).setDefaultValue(false)
                .setSaveConsumer(config::setEnforceHostFullOp)
                .setTooltip(Component.translatable("crepow.config.server.enforce_host_full_op.tooltip"))
                .build());

        return builder.build();
    }
}
