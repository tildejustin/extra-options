package me.voidxwalker.options.extra.screen;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.resource.language.I18n;

public class AccessibilityOptionsScreen extends Screen {
    private static final GameOptions.class_316[] OPTIONS = new GameOptions.class_316[]{
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

    @Override
    public void method_2224() {
        this.title = "Accessibility Settings...";
        int i = 0;

        for (GameOptions.class_316 option : OPTIONS) {
            if (option.method_1653()) {
                this.field_2564.add(new SliderWidget(option.method_1647(), this.field_2561 / 2 - 155 + i % 2 * 160, this.field_2559 / 6 + 24 * (i >> 1), option));
            } else {
                OptionButtonWidget optionButtonWidget = new OptionButtonWidget(
                        option.method_1647(), this.field_2561 / 2 - 155 + i % 2 * 160, this.field_2559 / 6 + 24 * (i >> 1), option, this.options.method_1642(option)
                );
                this.field_2564.add(optionButtonWidget);
            }
            i++;
        }

        this.field_2564.add(new ClickableWidget(200, this.field_2561 / 2 - 100, this.field_2559 / 6 + (OPTIONS.length + 1) / 2 * (20 + 4), I18n.translate("gui.done")));
    }

    // keyPressed
    @Override
    protected void method_0_2773(char id, int code) {
        if (code == 1) {
            this.field_2563.options.write();
        }
        super.method_0_2773(id, code);
    }

    // buttonPressed
    @Override
    protected void method_0_2778(ClickableWidget button) {
        if (button.field_2078) {
            if (button.field_2077 == 200) {
                this.field_2563.options.write();
                this.field_2563.setScreen(this.parent);
                return;
            }

            if (button instanceof OptionButtonWidget) {
                this.options.method_1629(((OptionButtonWidget) button).method_1899(), 1);
                button.field_2074 = this.options.method_1642(GameOptions.class_316.method_1655(button.field_2077));
            }
        }
    }

    // render
    @Override
    public void method_2214(int mouseX, int mouseY, float tickDelta) {
        this.method_2240();
        this.method_1789(this.field_2554, this.title, this.field_2561 / 2, 20, 16777215);
        super.method_2214(mouseX, mouseY, tickDelta);
    }
}
