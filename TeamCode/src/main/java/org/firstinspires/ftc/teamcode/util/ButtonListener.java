package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ButtonListener {

    private final List<Boolean> buttonList;
    private boolean button;
    private boolean toggleState;

    private ButtonListener() {
        this.button = false;
        this.toggleState = false;
        this.buttonList = new ArrayList<>();
    }

    public static ButtonBuilder whileTrue(boolean button) {
        return new ButtonBuilder().whileTrue(button);
    }

    public static ButtonBuilder toggleOnTrue(boolean button) {
        return new ButtonBuilder().toggleOnTrue(button);
    }

    public static class ButtonBuilder {
        private final ButtonListener ButtonListenerInstance;

        private ButtonBuilder() {
            ButtonListenerInstance = new ButtonListener();
        }

        public ButtonBuilder whileTrue(boolean button) {
            ButtonListenerInstance.buttonList.add(button);
            ButtonListenerInstance.button = button;
            return this;
        }

        public ButtonBuilder and(boolean button) {
            ButtonListenerInstance.buttonList.add(button);
            ButtonListenerInstance.button = ButtonListenerInstance.buttonList.stream().allMatch(Boolean::booleanValue);
            return this;
        }

        public ButtonBuilder or(boolean button) {
            ButtonListenerInstance.buttonList.add(button);
            ButtonListenerInstance.button = ButtonListenerInstance.buttonList.stream().anyMatch(Boolean::booleanValue);
            return this;
        }

        public ButtonBuilder toggleOnTrue(boolean button) {
            if (button) {
                ButtonListenerInstance.toggleState = !ButtonListenerInstance.toggleState;
            }
            ButtonListenerInstance.button = ButtonListenerInstance.toggleState;
            return this;
        }

        public void run(Runnable runnable) {
            this.run(runnable, Optional.empty());
        }

        public void run(Runnable runnable, Optional<Runnable> elseRunnableOptional) {
            if (ButtonListenerInstance.button) {
                runnable.run();
            } else {
                elseRunnableOptional.ifPresent(Runnable::run);
            }

            ButtonListenerInstance.buttonList.clear();
            ButtonListenerInstance.button = false;
        }
    }
}
