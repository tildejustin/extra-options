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
    protected void init() {
        this.title = "Accessibility Settings...";
        int i = 0;

        for (GameOptions.Option option : OPTIONS) {
            if (option.isNumeric()) {
                this.addButton(new OptionSliderWidget(option.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option));
            } else {
                OptionButtonWidget optionButtonWidget = new OptionButtonWidget(
                        option.getOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), option, this.options.method_18260(option)
                ) {
                    @Override
                    public void method_18374(double d, double e) {
                        options.method_18258(this.getOption(), 1);
                        this.message = options.method_18260(GameOptions.Option.byOrdinal(this.id));
                    }
                };
                this.addButton(optionButtonWidget);
            }
            i++;
        }

        this.addButton(new ButtonWidget(200, this.width / 2 - 100, this.height / 6 + +(OPTIONS.length + 1) / 2 * (20 + 4), I18n.translate("gui.done")) {
            @Override
            public void method_18374(double d, double e) {
                client.options.save();
                client.setScreen(parent);
            }
        });
    }

    @Override
    public void method_18608() {
        this.client.options.save();
        super.method_18608();
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();
        this.drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(mouseX, mouseY, tickDelta);
    }
}
