package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.constants.GlobalConstants;

public class GamepadButton {
    public Gamepad gamepad;
    public SingleButtonListener buttonA;
    public SingleButtonListener buttonB;
    public SingleButtonListener buttonY;
    public SingleButtonListener buttonX;
    public SingleButtonListener buttonDPadUp;
    public SingleButtonListener buttonDPadDown;
    public SingleButtonListener buttonDPadLeft;
    public SingleButtonListener buttonDPadRight;
    public SingleButtonListener buttonLeftBumper;
    public SingleButtonListener buttonRightBumper;
    public SingleButtonListener buttonLeftStickButton;
    public SingleButtonListener buttonRightStickButton;
    public SingleButtonListener buttonStart;
    public SingleButtonListener buttonBack;
    public SingleButtonListener buttonGuide;

    public GamepadButton(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.buttonA = new SingleButtonListener(gamepad.a);
        this.buttonB = new SingleButtonListener(gamepad.b);
        this.buttonY = new SingleButtonListener(gamepad.y);
        this.buttonX = new SingleButtonListener(gamepad.x);
        this.buttonDPadUp = new SingleButtonListener(gamepad.dpad_up);
        this.buttonDPadDown = new SingleButtonListener(gamepad.dpad_down);
        this.buttonDPadLeft = new SingleButtonListener(gamepad.dpad_left);
        this.buttonDPadRight = new SingleButtonListener(gamepad.dpad_right);
        this.buttonLeftBumper = new SingleButtonListener(gamepad.left_bumper);
        this.buttonRightBumper = new SingleButtonListener(gamepad.right_bumper);
        this.buttonLeftStickButton = new SingleButtonListener(gamepad.left_stick_button);
        this.buttonRightStickButton = new SingleButtonListener(gamepad.right_stick_button);
        this.buttonStart = new SingleButtonListener(gamepad.start);
        this.buttonBack = new SingleButtonListener(gamepad.back);
        this.buttonGuide = new SingleButtonListener(gamepad.guide);
    }

    public double getLeftStickY() {
        return gamepad.left_stick_y;
    }

    public double getLeftStickX() {
        return gamepad.left_stick_x;
    }

    public double getRightStickX() {
        return gamepad.right_stick_x;
    }

    public double getRightStickY() {
        return gamepad.right_stick_y;
    }

    public boolean isButtonA() {
        return gamepad.a;
    }

    public boolean isButtonB() {
        return gamepad.b;
    }

    public boolean isButtonX() {
        return gamepad.x;
    }

    public boolean isButtonY() {
        return gamepad.y;
    }

    public boolean isButtonDPadUp() {
        return gamepad.dpad_up;
    }

    public boolean isButtonDPadDown() {
        return gamepad.dpad_down;
    }

    public boolean isButtonDPadLeft() {
        return gamepad.dpad_left;
    }

    public boolean isButtonDPadRight() {
        return gamepad.dpad_right;
    }

    public boolean isButtonLeftBumper() {
        return gamepad.left_bumper;
    }

    public boolean isButtonRightBumper() {
        return gamepad.right_bumper;
    }

    public boolean isButtonLeftStickButton() {
        return gamepad.left_stick_button;
    }

    public boolean isButtonRightStickButton() {
        return gamepad.right_stick_button;
    }

    public double getLeftTrigger() {
        return gamepad.left_trigger;
    }

    public double getRightTrigger() {
        return gamepad.right_trigger;
    }

    public boolean isLeftTriggerPressed() {
        return gamepad.left_trigger > GlobalConstants.Controller.TRIGGER_PRESSED_THRESHOLD_VALUE;
    }

    public boolean isRightTriggerPressed() {
        return gamepad.right_trigger > GlobalConstants.Controller.TRIGGER_PRESSED_THRESHOLD_VALUE;
    }

    public boolean isButtonStart() {
        return gamepad.start;
    }

    public boolean isButtonBack() {
        return gamepad.back;
    }

    public boolean isButtonGuide() {
        return gamepad.guide;
    }

    public SingleButtonListener.ButtonBuilder whileButtonA() {
        return buttonA.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonB() {
        return buttonB.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonA() {
        return buttonA.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonB() {
        return buttonB.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonX() {
        return buttonX.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonY() {
        return buttonY.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonDPadUp() {
        return buttonDPadUp.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonDPadDown() {
        return buttonDPadDown.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonDPadLeft() {
        return buttonDPadLeft.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonDPadRight() {
        return buttonDPadRight.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonLeftBumper() {
        return buttonLeftBumper.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonRightBumper() {
        return buttonRightBumper.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonLeftStickButton() {
        return buttonLeftStickButton.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonRightStickButton() {
        return buttonRightStickButton.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonStart() {
        return buttonStart.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonBack() {
        return buttonBack.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder whileButtonGuide() {
        return buttonGuide.whileTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonX() {
        return buttonX.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonY() {
        return buttonY.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonDPadUp() {
        return buttonDPadUp.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonDPadDown() {
        return buttonDPadDown.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonDPadLeft() {
        return buttonDPadLeft.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonDPadRight() {
        return buttonDPadRight.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonLeftBumper() {
        return buttonLeftBumper.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonRightBumper() {
        return buttonRightBumper.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonLeftStickButton() {
        return buttonLeftStickButton.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonRightStickButton() {
        return buttonRightStickButton.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonStart() {
        return buttonStart.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonBack() {
        return buttonBack.toggleOnTrue();
    }

    public SingleButtonListener.ButtonBuilder toggleOnButtonGuide() {
        return buttonGuide.toggleOnTrue();
    }

}
