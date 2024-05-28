package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.util.StaticHeading.Mode.ANGLE;
import static org.firstinspires.ftc.teamcode.constants.IntakeConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.ButtonUtils;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class Intake implements Subsystem {
    private static Intake instance;
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private TouchSensor limitRight;
    private TouchSensor limitLeft;
    private StaticHeading pidController;

    private Intake() {
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        motorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);
        motorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        limitRight = hardwareMap.get(TouchSensor.class, LIMIT_RIGHT);
        limitLeft = hardwareMap.get(TouchSensor.class, LIMIT_LEFT);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidController = new StaticHeading(PID.kP, PID.kI, PID.kD, PID.kF, ANGLE);

        telemetry.addData("Intake Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {
        pidController.PIDControl(TARGET_DEGREE, motorLeft.getCurrentPosition());

        ButtonUtils.whileHeld(operator.isButtonLeftBumper())
                .and(operator.isButtonRightBumper())
                .then(this::openBothIntakes);

        ButtonUtils.whileHeld(operator.isButtonLeftBumper())
                .then(this::openLeftIntake);

        ButtonUtils.whileHeld(operator.isButtonRightBumper())
                .then(this::openRightIntake);

        ButtonUtils.whileHeld(operator.isLeftTriggerPressed())
                .and(operator.isRightTriggerPressed())
                .then(() -> closeBothIntakes(operator));

        ButtonUtils.whileHeld(operator.isLeftTriggerPressed())
                .then(() -> handleLeftTrigger(operator));

        ButtonUtils.whileHeld(operator.isRightTriggerPressed())
                .then(() -> handleRightTrigger(operator));

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    private void openLeftIntake() {
        motorRight.setPower(0);
        pidController.setPowerMotor(motorLeft, CORE_HEX_TICKS_PER_REVOLUTION);
    }

    private void openRightIntake() {
        motorLeft.setPower(0);
        pidController.setPowerMotor(motorRight, CORE_HEX_TICKS_PER_REVOLUTION);
    }

    private void openBothIntakes() {
        pidController.setPowerMotor(motorLeft, CORE_HEX_TICKS_PER_REVOLUTION);
        pidController.setPowerMotor(motorRight, CORE_HEX_TICKS_PER_REVOLUTION);
    }

    private void closeBothIntakes(SmartController operator) {
        if (!isLimitRight()) {
            motorRight.setPower(operator.getRightTrigger());
        }
        if (!isLimitLeft()) {
            motorLeft.setPower(operator.getLeftTrigger());
        }
    }

    private void handleLeftTrigger(SmartController operator) {
        if (!isLimitLeft()) {
            motorLeft.setPower(operator.getLeftTrigger());
        } else {
            resetEncoder(motorLeft);
            operator.rumble(1, 0, 200);
        }
    }

    private void handleRightTrigger(SmartController operator) {
        if (!isLimitRight()) {
            motorRight.setPower(operator.getRightTrigger());
        } else {
            resetEncoder(motorRight);
            operator.rumble(0, 1, 200);
        }
    }

    private void resetEncoder(DcMotor motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Sensor Methods
    public boolean isLimitRight() {
        return limitRight.isPressed();
    }

    public boolean isLimitLeft() {
        return limitLeft.isPressed();
    }

    // Singleton Instance
    public static synchronized Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }
}