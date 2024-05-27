package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;
import java.util.List;

public class ButtonUtils {

    private final List<Boolean> buttonList;
    private boolean button;
    private boolean toggleState;

    private ButtonUtils() {
        this.button = false;
        this.toggleState = false;
        this.buttonList = new ArrayList<>();
    }

    public static ButtonBuilder whileHeld(boolean button) {
        return new ButtonBuilder().whileHeld(button);
    }

    public static ButtonBuilder toggleWhenPressed(boolean button) {
        return new ButtonBuilder().toggleWhenPressed(button);
    }

    public static class ButtonBuilder {
        private final ButtonUtils buttonUtilsInstance;

        private ButtonBuilder() {
            buttonUtilsInstance = new ButtonUtils();
        }

        public ButtonBuilder whileHeld(boolean button) {
            buttonUtilsInstance.buttonList.add(button);
            buttonUtilsInstance.button = button;
            return this;
        }

        public ButtonBuilder and(boolean button) {
            buttonUtilsInstance.buttonList.add(button);
            buttonUtilsInstance.button = buttonUtilsInstance.buttonList.stream().allMatch(Boolean::booleanValue);
            return this;
        }

        public ButtonBuilder or(boolean button) {
            buttonUtilsInstance.buttonList.add(button);
            buttonUtilsInstance.button = buttonUtilsInstance.buttonList.stream().anyMatch(Boolean::booleanValue);
            return this;
        }

        public ButtonBuilder toggleWhenPressed(boolean button) {
            if (button) {
                buttonUtilsInstance.toggleState = !buttonUtilsInstance.toggleState;
            }
            buttonUtilsInstance.button = buttonUtilsInstance.toggleState;
            return this;
        }

        public void then(Runnable runnable) {
            if (buttonUtilsInstance.button) {
                runnable.run();
            }

            buttonUtilsInstance.buttonList.clear();
            buttonUtilsInstance.button = false;
        }
    }
}
