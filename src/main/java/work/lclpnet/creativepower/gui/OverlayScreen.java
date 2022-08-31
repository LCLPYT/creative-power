package work.lclpnet.creativepower.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import work.lclpnet.creativepower.client.action.FloodFillAction;
import work.lclpnet.creativepower.client.action.IAction;

import java.util.List;

public class OverlayScreen extends Screen {

    private boolean focused = false;

    public OverlayScreen() {
        super(new TranslatableText("gui.crepow.overlay"));
    }

    @Override
    protected void init() {
        addActionButtons();
    }

    protected void addActionButtons() {
        final List<IAction> actions = List.of(
                new FloodFillAction()
        );

        final int height = 50, spacingY = 10;

        for (int i = 0; i < actions.size(); i++) {
            var action = actions.get(i);

            var btn = new ButtonWidget(
                    10, 10 + i * (height + spacingY), 200, height,
                    new TranslatableText("crepow.action.%s".formatted(action.getName())),
                    action::onClicked
            );

            addDrawableChild(btn);
        }
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
