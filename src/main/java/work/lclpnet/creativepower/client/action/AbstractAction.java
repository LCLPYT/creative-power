package work.lclpnet.creativepower.client.action;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public abstract class AbstractAction implements IAction {

    private final String name;

    protected AbstractAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
