package me.voidxwalker.options.extra.screen;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.class_394;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.*;
import net.minecraft.util.Language;

public class AccessibilityOptionsScreen extends Screen {
    private static final GameOption[] OPTIONS = new GameOption[]{
            ExtraOptions.DISTORTION_EFFECT_SCALE,
            ExtraOptions.FOV_EFFECT_SCALE,
            ExtraOptions.CONTROL_BOW_FOV,
            ExtraOptions.CONTROL_SUBMERGED_FOV
    };
    private final Screen parent;
    private final GameOptions options;
    private String title;

    public AccessibilityOptionsScreen(Screen parent, GameOptions options) {
        this.parent = parent;
        this.options = options;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        this.title = "Accessibility Settings...";
        int i = 0;

        for (GameOption option : OPTIONS) {
            if (option.method_879()) {
                this.buttons.add(new class_394(option.method_882(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option, this.options.getStringOption(option), this.options.getFLoatOption(option)));
            } else {
                OptionButtonWidget optionButtonWidget = new OptionButtonWidget(
                        option.method_882(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option, this.options.getStringOption(option)
                );
                this.buttons.add(optionButtonWidget);
            }
            i++;
        }

        this.buttons.add(new ButtonWidget(200, this.width / 2 - 100, this.height / 6 + (OPTIONS.length + 1) / 2 * (20 + 4), Language.getInstance().translate("gui.done")));
    }

    @Override
    protected void keyPressed(char id, int code) {
        if (code == 1) {
            this.field_1229.options.save();
        }
        super.keyPressed(id, code);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.active) {
            if (button.id == 200) {
                this.field_1229.options.save();
                this.field_1229.openScreen(this.parent);
                return;
            }

            if (button instanceof OptionButtonWidget) {
                this.options.setOption(((OptionButtonWidget) button).method_1088(), 1);
                button.message = this.options.getStringOption(GameOption.method_880(button.id));
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
