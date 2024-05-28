package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ButtonListener is a utility class that listens for a button press and runs a Runnable if the button is pressed
 * or a Runnable if the button is not pressed.
 * <p>
 * It can also listen for multiple buttons and do comparisons.
 */
public class ButtonListener {

    private final List<Boolean> buttonList;
    private boolean button;
    private boolean toggleState;

    private ButtonListener() {
        this.button = false;
        this.toggleState = false;
        this.buttonList = new ArrayList<>();
    }

    /**
     * Creates a new ButtonBuilder instance with the given boolean value button.
     * <p>
     * ButtonBuilder is a builder class that allows for chaining of conditions.
     *
     * @param button the button to listen for
     * @return a new ButtonBuilder instance
     */
    public static ButtonBuilder whileTrue(boolean button) {
        return new ButtonBuilder().whileTrue(button);
    }

    /**
     * Creates a new ButtonBuilder instance with the given boolean value button.
     * <p>
     * ButtonBuilder is a builder class that allows for chaining of conditions.
     *
     * @param button the button to listen for
     * @return a new ButtonBuilder instance
     */
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

        /**
         * Adds a condition to the button listener.
         *
         * @param condition the condition to add
         * @return the ButtonBuilder instance
         */
        public ButtonBuilder and(boolean condition) {
            ButtonListenerInstance.buttonList.add(condition);
            ButtonListenerInstance.button = ButtonListenerInstance.buttonList.stream().allMatch(Boolean::booleanValue);
            return this;
        }

        /**
         * Adds a condition to the button listener.
         *
         * @param condition the condition to add
         * @return the ButtonBuilder instance
         */
        public ButtonBuilder or(boolean condition) {
            ButtonListenerInstance.buttonList.add(condition);
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

        /**
         * Runs the given Runnable.
         *
         * @param runnable the Runnable to run
         */
        public void run(Runnable runnable) {
            this.run(runnable, Optional.empty());
        }

        /**
         * Runs the given Runnable if the conditions are true, otherwise runs the elseRunnable.
         *
         * @param runnable the Runnable to run if the conditions are true
         * @param elseRunnableOptional the Runnable to run if the conditions are false
         */
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
