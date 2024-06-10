package me.voidxwalker.options.extra.screen;

import me.voidxwalker.options.extra.ExtraOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.*;
import net.minecraft.client.resource.language.I18n;

public class AccessibilityOptionsScreen extends Screen {
    private static final GameOption[] OPTIONS = new GameOption[]{
            ExtraOptions.DISTORTION_EFFECT_SCALE,
            ExtraOptions.FOV_EFFECT_SCALE,
            ExtraOptions.BOW_FOV_EFFECTS,
            ExtraOptions.SUBMERGED_FOV_EFFECTS
    };
    private final Screen parent;
    private final GameOptions options;
    private String title;
    private boolean optifineWarning = false;

    public AccessibilityOptionsScreen(Screen parent, GameOptions options) {
        this.parent = parent;
        this.options = options;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void method_21947() {
        this.title = "Accessibility Settings...";
        int i = 0;

        for (GameOption option : OPTIONS) {
            if (option.method_879()) {
                this.field_22537.add(new OptionSliderWidget(option.method_882(), this.field_22535 / 2 - 155 + i % 2 * 160, this.field_22536 / 6 + 24 * (i >> 1), option));
            } else {
                OptionButtonWidget optionButtonWidget = new OptionButtonWidget(
                        option.method_882(), this.field_22535 / 2 - 155 + i % 2 * 160, this.field_22536 / 6 + 24 * (i >> 1), option, this.options.getStringOption(option)
                );
                this.field_22537.add(optionButtonWidget);
            }
            i++;
        }

        this.field_22537.add(new ButtonWidget(200, this.field_22535 / 2 - 100, this.field_22536 / 6 + (OPTIONS.length + 1) / 2 * (20 + 4), I18n.translate("gui.done")));
    }

    protected void method_21924(char id, int code) {
        if (code == 1) {
            this.field_22534.options.save();
        }
        super.method_21924(id, code);
    }

    @Override
    protected void method_21930(ButtonWidget button) {
        if (button instanceof OptionButtonWidget) {
            GameOption option = ((OptionButtonWidget) button).method_1088();
            // disable boolean option buttons when optifine is loaded (id > 100), avoiding a crash
            if (option.method_882() > 100 && (option == ExtraOptions.BOW_FOV_EFFECTS || option == ExtraOptions.SUBMERGED_FOV_EFFECTS)) {
                optifineWarning = true;
                return;
            }
        }

        if (button.field_22511) {
            if (button.id == 200) {
                this.field_22534.options.save();
                this.field_22534.setScreen(this.parent);
                return;
            }

            if (button instanceof OptionButtonWidget) {
                this.options.setOption(((OptionButtonWidget) button).method_1088(), 1);
                button.field_22510 = this.options.getStringOption(GameOption.method_880(button.id));
            }
        }
    }

    @Override
    public void method_21925(int i, int j, float f) {
        this.method_21946();
        this.method_21881(this.field_22540, this.title, this.field_22535 / 2, 20, 16777215);
        if (optifineWarning) {
            this.method_21881(this.field_22540, "Boolean options cannot be changed in-game while OptiFine is loaded!", this.field_22535 / 2, 31, 0xeed202);
        }
        super.method_21925(i, j, f);
    }
}
