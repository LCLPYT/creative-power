package work.lclpnet.creativepower.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;

@Environment(EnvType.CLIENT)
public interface IAction {

    String getName();

    void onClicked(ButtonWidget clicked);
}
