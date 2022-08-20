package work.lclpnet.creativepower.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class OverlayScreen extends Screen {

    private boolean focused = false;

    public OverlayScreen() {
        super(new TranslatableText("gui.crepow.overlay"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, getTitle(), this.width / 2, 20, 0xFFFFFF);
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void close() {
        focused = false;
        super.close();
    }
}
