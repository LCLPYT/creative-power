package work.lclpnet.creativepower.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;
import work.lclpnet.creativepower.Config;
import work.lclpnet.creativepower.core.service.FloodFillService;
import work.lclpnet.creativepower.util.InvUtils;

@Environment(EnvType.CLIENT)
public class FloodFillAction extends AbstractAction {

    public static final String NAME = "flood_fill";

    public FloodFillAction() {
        super(NAME);
    }

    @Override
    public void onClicked(ButtonWidget clicked) {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null) throw new IllegalStateException("Player must not be null");

        var optional = InvUtils.getBlockFromPlayerHands(mc.player);
        if (optional.isEmpty()) {
            mc.player.sendMessage(new TranslatableText("error.no_block_in_hand"), false);
            return;
        }

        var pos = mc.player.getBlockPos();

//        FloodFillService.fill(pos, mc.player.world, Config.floodFillSettings().maxDistance); // TODO
    }

    public static class Settings {
        public int maxDistance = 32;
    }
}
