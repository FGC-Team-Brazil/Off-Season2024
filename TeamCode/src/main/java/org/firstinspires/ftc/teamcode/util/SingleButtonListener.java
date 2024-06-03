package org.firstinspires.ftc.teamcode.util;

import java.util.ArrayList;
import java.util.List;

public class SingleButtonListener {

    private final List<Boolean> buttonList;
    private boolean button;
    private boolean toggleState;

    public SingleButtonListener(boolean button) {
        this.button = button;
        this.toggleState = false;
        this.buttonList = new ArrayList<>();
    }

    /**
     * Creates a new ButtonBuilder instance with the given boolean value button.
     * <p>
     * ButtonBuilder is a builder class that allows for chaining of conditions.
     *
     * @return a new ButtonBuilder instance
     */
    public SingleButtonListener.ButtonBuilder whileTrue() {
        return new SingleButtonListener.ButtonBuilder(this).whileTrue(this.button);
    }

    /**
     * Creates a new ButtonBuilder instance with the given boolean value button.
     * <p>
     * ButtonBuilder is a builder class that allows for chaining of conditions.
     *
     * @return a new ButtonBuilder instance
     */
    public SingleButtonListener.ButtonBuilder toggleOnTrue() {
        return new SingleButtonListener.ButtonBuilder(this).toggleOnTrue(this.button);
    }

    public static class ButtonBuilder {
        private final SingleButtonListener buttonListenerInstance;

        private ButtonBuilder(SingleButtonListener instance) {
            this.buttonListenerInstance = instance;
        }

        public SingleButtonListener.ButtonBuilder whileTrue(boolean button) {
            buttonListenerInstance.buttonList.add(button);
            buttonListenerInstance.button = button;
            return this;
        }

        /**
         * Adds a condition to the button listener.
         *
         * @param condition the condition to add
         * @return the ButtonBuilder instance
         */
        public SingleButtonListener.ButtonBuilder and(boolean condition) {
            buttonListenerInstance.buttonList.add(condition);
            buttonListenerInstance.button = buttonListenerInstance.buttonList.stream().allMatch(Boolean::booleanValue);
            return this;
        }

        /**
         * Adds a condition to the button listener.
         *
         * @param condition the condition to add
         * @return the ButtonBuilder instance
         */
        public SingleButtonListener.ButtonBuilder or(boolean condition) {
            buttonListenerInstance.buttonList.add(condition);
            buttonListenerInstance.button = buttonListenerInstance.buttonList.stream().anyMatch(Boolean::booleanValue);
            return this;
        }

        public SingleButtonListener.ButtonBuilder toggleOnTrue(boolean button) {
            if (button) {
                buttonListenerInstance.toggleState = !buttonListenerInstance.toggleState;
            }
            buttonListenerInstance.button = buttonListenerInstance.toggleState;
            return this;
        }

        /**
         * Runs the given Runnable.
         * <p>
         * Its possible pass a lambda expression or a method reference.
         *
         * @param runnable the Runnable to run
         */
        public void run(Runnable runnable) {
            if (buttonListenerInstance.button) {
                runnable.run();
            }

            buttonListenerInstance.buttonList.clear();
            buttonListenerInstance.button = false;
        }

        /**
         * Runs the given Runnable if the conditions are true, otherwise runs the elseRunnable.
         * <p>
         * Its possible pass a lambda expression or a method reference.
         *
         * @param runnable     the Runnable to run if the conditions are true
         * @param elseRunnable the Runnable to run if the conditions are false
         */
        public void run(Runnable runnable, Runnable elseRunnable) {
            if (buttonListenerInstance.button) {
                runnable.run();
            } else {
                elseRunnable.run();
            }

            buttonListenerInstance.buttonList.clear();
            buttonListenerInstance.button = false;
        }
    }
}