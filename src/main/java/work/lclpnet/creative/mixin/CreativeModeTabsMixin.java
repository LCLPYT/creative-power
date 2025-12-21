package work.lclpnet.creative.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.creative.util.ItemGroupUpdater;

@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {

    @Shadow @Nullable
    private static CreativeModeTab.@Nullable ItemDisplayParameters CACHED_PARAMETERS;

    @Shadow
    private static void buildAllTabContents(CreativeModeTab.ItemDisplayParameters displayContext) {}

    @Inject(
            method = "tryRebuildTabContents",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void crepow$beforeUpdateDisplayContext(FeatureFlagSet enabledFeatures, boolean operatorEnabled,
                                                          HolderLookup.Provider lookup, CallbackInfoReturnable<Boolean> cir) {
        if (!ItemGroupUpdater.isDirty()) return;
        ItemGroupUpdater.setDirty(false);

        CACHED_PARAMETERS = new CreativeModeTab.ItemDisplayParameters(enabledFeatures, operatorEnabled, lookup);
        buildAllTabContents(CACHED_PARAMETERS);
        cir.setReturnValue(true);
    }
}
