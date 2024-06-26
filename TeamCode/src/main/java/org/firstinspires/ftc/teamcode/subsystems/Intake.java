package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.util.StaticHeading.Mode.ANGLE;
import static org.firstinspires.ftc.teamcode.constants.IntakeConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.ButtonListener;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class Intake implements Subsystem {
    private static Intake instance;
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private TouchSensor limitRight;
    private TouchSensor limitLeft;
    private StaticHeading PIDController;

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

        PIDController = new StaticHeading(PID.kP, PID.kI, PID.kD, PID.kF, ANGLE);

        telemetry.addData("Intake Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {
        PIDController.calculate(TARGET_DEGREE, motorLeft.getCurrentPosition());

        ButtonListener.whileTrue(operator.isButtonLeftBumper())
                .and(operator.isButtonRightBumper())
                .run(() -> {
                    PIDController.setPowerMotor(motorLeft, CORE_HEX_TICKS_PER_REVOLUTION);
                    PIDController.setPowerMotor(motorRight, CORE_HEX_TICKS_PER_REVOLUTION);
                });

        ButtonListener.whileTrue(operator.isButtonLeftBumper())
                .run(() -> {
                    motorRight.setPower(0);
                    PIDController.setPowerMotor(motorLeft, CORE_HEX_TICKS_PER_REVOLUTION);
                });

        ButtonListener.whileTrue(operator.isButtonRightBumper())
                .run(() -> {
                    motorLeft.setPower(0);
                    PIDController.setPowerMotor(motorRight, CORE_HEX_TICKS_PER_REVOLUTION);
                });

        ButtonListener.whileTrue(operator.isLeftTriggerPressed())
                .and(operator.isRightTriggerPressed())
                .and(!isLimitRight())
                .and(!isLimitLeft())
                .run(() -> {
                    motorRight.setPower(operator.getRightTrigger());
                    motorLeft.setPower(operator.getLeftTrigger());
                });

        ButtonListener.whileTrue(operator.isLeftTriggerPressed())
                .and(!isLimitLeft())
                .run(() -> {
                    motorLeft.setPower(operator.getLeftTrigger());
                }, () -> {
                    resetEncoder(motorLeft);
                    operator.rumble(1, 0, 200);
                });

        ButtonListener.whileTrue(operator.isRightTriggerPressed())
                .and(!isLimitRight())
                .run(() -> {
                    motorRight.setPower(operator.getRightTrigger());
                }, () -> {
                    resetEncoder(motorRight);
                    operator.rumble(0, 1, 200);
                });

        stop();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        motorRight.setPower(0);
        motorLeft.setPower(0);
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