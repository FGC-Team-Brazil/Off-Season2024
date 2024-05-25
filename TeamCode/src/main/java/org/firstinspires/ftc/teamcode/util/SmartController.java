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

    public boolean getButtonA() {
        return gamepad.a;
    }

    public boolean getButtonB() {
        return gamepad.b;
    }

    public boolean getButtonX() {
        return gamepad.x;
    }

    public boolean getButtonY() {
        return gamepad.y;
    }

    public boolean getButtonDPadUp() {
        return gamepad.dpad_up;
    }

    public boolean getButtonDPadDown() {
        return gamepad.dpad_down;
    }

    public boolean getButtonDPadLeft() {
        return gamepad.dpad_left;
    }

    public boolean getButtonDPadRight() {
        return gamepad.dpad_right;
    }

    public boolean getButtonLeftBumper() {
        return gamepad.left_bumper;
    }

    public boolean getButtonRightBumper() {
        return gamepad.right_bumper;
    }

    public boolean getButtonLeftStickButton() {
        return gamepad.left_stick_button;
    }

    public boolean getButtonRightStickButton() {
        return gamepad.right_stick_button;
    }

    public double getLeftTrigger() {
        return gamepad.left_trigger;
    }

    public double getRightTrigger() {
        return gamepad.right_trigger;
    }

    public boolean getButtonStart() {
        return gamepad.start;
    }

    public boolean getButtonBack() {
        return gamepad.back;
    }

    public boolean getButtonGuide() {
        return gamepad.guide;
    }

    public void rumble(double leftRumble, double rightRumble) {
        gamepad.rumble(leftRumble, rightRumble, 0);
    }

    public void rumble(double rumble) {
        gamepad.rumble(rumble, rumble, 0);
    }

    public void rumbleBlips(int counts) {
        gamepad.rumbleBlips(counts);
    }

}
