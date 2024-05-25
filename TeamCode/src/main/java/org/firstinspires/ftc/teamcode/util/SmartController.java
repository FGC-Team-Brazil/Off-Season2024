package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class SmartController {
    public Gamepad gamepad;

    public SmartController(Gamepad gamepad) {
        this.gamepad = gamepad;
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
        return gamepad.left_trigger > 0.3;
    }

    public boolean isRightTriggerPressed() {
        return gamepad.right_trigger > 0.3;
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

    public void rumble(double leftRumble, double rightRumble, int milliseconds) {
        gamepad.rumble(leftRumble, rightRumble, milliseconds);
    }

    public void rumble(double rumble, int milliseconds) {
        gamepad.rumble(rumble, rumble, 0);
    }

    public void rumbleBlips(int counts) {
        gamepad.rumbleBlips(counts);
    }

}
