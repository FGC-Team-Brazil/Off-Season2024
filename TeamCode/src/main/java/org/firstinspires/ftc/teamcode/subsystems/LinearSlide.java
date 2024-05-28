package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.constants.LinearSlideConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

import org.firstinspires.ftc.teamcode.util.ButtonListener;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class LinearSlide implements Subsystem {

    private static LinearSlide instance;


    public double currentPIDGoal = 0;
    private DcMotor liftMotorRight;
    private DcMotor liftMotorLeft;
    private DcMotor BotHangMotor;
    private TouchSensor magneticLimit;


    StaticHeading leftPID;
    StaticHeading rightPID;
    StaticHeading botHangPID;


    public enum linearMode {
        NORMAL,
        DEPOSIT,
        HANG
    }

    public double endHeightGoal = 0;
    private double liftSetpointInTicks = 0;
    private double hangSetpointInTicks = 0;

    public linearMode currentMode = linearMode.NORMAL;

    private LinearSlide() {
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {

        liftMotorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        liftMotorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);
        BotHangMotor = hardwareMap.get(DcMotor.class, MOTOR_HANG);
        magneticLimit = hardwareMap.get(TouchSensor.class, MAGNETIC_LIMIT);
        leftPID = new StaticHeading(kP, kI, kD, kF);
        rightPID = new StaticHeading(kP, kI, kD, kF);
        botHangPID = new StaticHeading(kPH, kIH, kDH, kFH);

        liftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BotHangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BotHangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("LinearSlide Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {

        ButtonListener.whileTrue(operator.isButtonX())
                .run(() -> endHeightGoal = DEPOSIT_HEIGHT_MID);

        ButtonListener.whileTrue(operator.isButtonY())
                .run(() -> endHeightGoal = DEPOSIT_HEIGHT_HIGH);

        ButtonListener.whileTrue(operator.isButtonA())
                .run(() -> endHeightGoal = 0);

        ButtonListener.whileTrue(operator.isRightTriggerPressed())
                .and(endHeightGoal == DEPOSIT_HEIGHT_MID)
                .run(() -> currentMode = linearMode.HANG);

        ButtonListener.whileTrue(operator.isButtonDPadUp())
                .run(() -> currentMode = linearMode.DEPOSIT);


        boolean physicalLimitReached = magneticLimit.isPressed();//todo replace false's with sensor detecting slide limit conditions

        switch (currentMode) {
            case NORMAL:
            case DEPOSIT:
                goToHeight(endHeightGoal, physicalLimitReached);
                break;
            case HANG:
                hangRobot();
                break;
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        liftMotorRight.setPower(0);
        liftMotorLeft.setPower(0);
    }

    private void setLiftPower(double leftPower, double rightPower) {
        liftMotorLeft.setPower(leftPower);
        liftMotorRight.setPower(rightPower);
    }

    private void setHangMotorPower(double power) {
        BotHangMotor.setPower(power);
    }

    // Command Methods
    private void goToHeight(double setpoint, boolean forceStop) {
        if (!forceStop) {
            advanceGoal(setpoint);
            liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
            hangSetpointInTicks = convertMetersToTicks(getAverageEncoderMeters() + HANG_MOTOR_SLACK);


            setLiftPower(leftPID.PIDControl(liftSetpointInTicks, liftMotorLeft.getCurrentPosition()),
                    rightPID.PIDControl(liftSetpointInTicks, liftMotorRight.getCurrentPosition())
            );
            setHangMotorPower(botHangPID.PIDControl(hangSetpointInTicks, BotHangMotor.getCurrentPosition()));
        } else {
            setLiftPower(0, 0);
            setHangMotorPower(0);
        }
    }

    private void hangRobot() {
        setLiftPower(0, 0);
        advanceGoal(ROBOT_HANG_HEIGHT);
        liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
        setHangMotorPower(botHangPID.PIDControl(hangSetpointInTicks, BotHangMotor.getCurrentPosition()));
    }

    // Encoder Methods
    public double convertTicksToMeters(double ticks) {
        return (((ticks / HD_HEX_TICKS_PER_REVOLUTION) / MOTORS_REDUCTION) * PULLEY_CIRCUMFERENCE);
    }

    public double convertMetersToTicks(double meters) {
        return (((meters * HD_HEX_TICKS_PER_REVOLUTION) * MOTORS_REDUCTION) / PULLEY_CIRCUMFERENCE);
    }

    public double getAverageEncoderTicks() {
        return ((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }

    public double getAverageEncoderMeters() {
        return convertTicksToMeters((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }

    private void advanceGoal(double setpoint) {
        if (currentPIDGoal + GOAl_STEP > setpoint && currentPIDGoal - GOAl_STEP < setpoint) {
            currentPIDGoal = setpoint;
        } else {
            if (currentPIDGoal > setpoint) {
                currentPIDGoal -= GOAl_STEP;
            } else if (currentPIDGoal < setpoint) {
                currentPIDGoal += GOAl_STEP;
            }
        }
    }

    public static synchronized LinearSlide getInstance() {
        if (instance == null) {
            instance = new LinearSlide();
        }
        return instance;
    }
}