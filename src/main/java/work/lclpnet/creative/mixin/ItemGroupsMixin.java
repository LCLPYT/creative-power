package work.lclpnet.creative.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.creative.util.ItemGroupUpdater;

@Mixin(ItemGroups.class)
public class ItemGroupsMixin {

    @Shadow @Nullable
    private static ItemGroup.@Nullable DisplayContext displayContext;

    @Shadow
    private static void updateEntries(ItemGroup.DisplayContext displayContext) {}

    @Inject(
            method = "updateDisplayContext",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void crepow$beforeUpdateDisplayContext(FeatureSet enabledFeatures, boolean operatorEnabled,
                                                          RegistryWrapper.WrapperLookup lookup, CallbackInfoReturnable<Boolean> cir) {
        if (!ItemGroupUpdater.isDirty()) return;
        ItemGroupUpdater.setDirty(false);

        displayContext = new ItemGroup.DisplayContext(enabledFeatures, operatorEnabled, lookup);
        updateEntries(displayContext);
        cir.setReturnValue(true);
    }
}
