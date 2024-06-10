package me.voidxwalker.options.extra.screen;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.resource.language.I18n;

public class AccessibilityOptionsScreen extends Screen {
    private static final GameOptions.Option[] OPTIONS = new GameOptions.Option[]{
            ExtraOptions.DISTORTION_EFFECT_SCALE,
            ExtraOptions.FOV_EFFECT_SCALE,
            ExtraOptions.BOW_FOV_EFFECTS,
            ExtraOptions.SUBMERGED_FOV_EFFECTS
    };
    private final Screen parent;
    private final GameOptions options;
    private String title;

    public AccessibilityOptionsScreen(Screen parent, GameOptions options) {
        this.parent = parent;
        this.options = options;
    }

    @Override
    public void init() {
        this.title = "Accessibility Settings...";
        int i = 0;

        for (GameOptions.Option option : OPTIONS) {
            if (option.isNumeric()) {
                this.buttons.add(new OptionSliderWidget(option.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option));
            } else {
                OptionButtonWidget optionButtonWidget = new OptionButtonWidget(
                        option.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option, this.options.getValueMessage(option)
                );
                this.buttons.add(optionButtonWidget);
            }
            i++;
        }

        this.buttons.add(new ButtonWidget(200, this.width / 2 - 100, this.height / 6 + (OPTIONS.length + 1) / 2 * (20 + 4), I18n.translate("gui.done")));
    }

    @Override
    protected void keyPressed(char id, int code) {
        if (code == 1) {
            this.client.options.save();
        }
        super.keyPressed(id, code);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.active) {
            if (button.id == 200) {
                this.client.options.save();
                this.client.setScreen(this.parent);
                return;
            }

            if (button instanceof OptionButtonWidget) {
                this.options.getBooleanValue(((OptionButtonWidget) button).getOption(), 1);
                button.message = this.options.getValueMessage(GameOptions.Option.byOrdinal(button.id));
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();
        this.drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(mouseX, mouseY, tickDelta);
    }
}
