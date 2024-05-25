package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.constants.LinearSlideConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class LinearSlide implements Subsystem {

    private static LinearSlide instance;

    private DcMotor motorRight;
    private DcMotor motorLeft;

    private StaticHeading pidController;

    public LinearSlide(){}

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        motorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        motorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);

        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidController = new StaticHeading(kP, kI, kD, kF);

        telemetry.addData("LinearSlide Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {
        setPower(operator.getLeftStickY(), operator.getLeftStickY());
        if (operator.isButtonA() != operator.isButtonY()) {
            while(operator.isButtonY()) {
                goToHeight(pidController, BARGE_LV3_HEIGHT, getAverageEncoderMeters());
            }
            while(operator.isButtonA()) {
                goToHeight(pidController, BARGE_LV2_HEIGHT, getAverageEncoderMeters());
            }
        }
    }

    @Override
    public void stop() {
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    private void setPower(double leftPower, double rightPower) {
        motorLeft.setPower(leftPower);
        motorRight.setPower(rightPower);
    }

    // Command Methods
    private void goToHeight(StaticHeading pidController, double setpoint, double state) {
        setPower(pidController.PIDControl(setpoint, state), pidController.PIDControl(setpoint, state));
    }

    // Encoder Methods
    public double convertTicksToMeters(double ticks) {
        return (((ticks / HD_HEX_TICKS_PER_REVOLUTION) / MOTORS_REDUCTION) * PULLEY_CIRCUMFERENCE);
    }

    public double getAverageEncoderTicks() {
        return ((double) (motorLeft.getCurrentPosition() + motorRight.getCurrentPosition()) / 2);
    }

    public double getAverageEncoderMeters() {
        return convertTicksToMeters((double) (motorLeft.getCurrentPosition() + motorRight.getCurrentPosition()) / 2);
    }

    // Singleton Instance
    public static synchronized LinearSlide getInstance() {
        if(instance == null){
            instance = new LinearSlide();
        }
        return instance;
    }
}