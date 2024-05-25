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


    public double currentPIDGoal=0;
    private DcMotor liftMotorRight;
    private DcMotor liftMotorLeft;
    private DcMotor boxTiltMotor;


    StaticHeading leftPID;
    StaticHeading rightPID;
    StaticHeading boxTiltPID;


    public enum linearMode{
        NORMAL,
        DEPOSIT,
        HANG
    }
    private double endHeightGoal =0;
    private double liftSetpointInTicks=0;
    private double boxSetpointInTicks =0;

    public linearMode currentMode = linearMode.NORMAL;
    public LinearSlide(){}
    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {

        liftMotorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        liftMotorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);
        boxTiltMotor = hardwareMap.get(DcMotor.class,MOTOR_BOX);
        leftPID = new StaticHeading(kP,kI,kD,kF);
        rightPID = new StaticHeading(kP,kI,kD,kF);
        boxTiltPID = new StaticHeading(kPH,kIH,kDH,kFH);

        liftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        boxTiltMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        boxTiltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("LinearSlide Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {

        if (operator.isButtonA()){
            endHeightGoal= DEPOSIT_HEIGHT_MID;
        } else if (operator.isButtonY()){
            endHeightGoal = DEPOSIT_HEIGHT_HIGH;

        }

        if (endHeightGoal== DEPOSIT_HEIGHT_MID &&operator.getRightTrigger()>0.9){
            currentMode=linearMode.HANG;
        }

        boolean physicalLimitReached = false||false;//todo replace false's with sensor detecting slide limit conditions

        switch (currentMode){
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
    public void stop(){
        liftMotorRight.setPower(0);
        liftMotorLeft.setPower(0);

    }

    private void setLiftPower(double leftPower, double rightPower) {
        liftMotorLeft.setPower(leftPower);
        liftMotorRight.setPower(rightPower);
    }

    private void setBoxMotorPower(double power){
        boxTiltMotor.setPower(power);
    }

    // Command Methods


    private void goToHeight(double setpoint, boolean forceStop){
        if (!forceStop) {
            advanceGoal(setpoint);
            liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
            switch (currentMode){
                case DEPOSIT:
                    boxSetpointInTicks = convertMetersToTicks(getAverageEncoderMeters()+BOX_MOTOR_DEPOSIT_CONTRACTION);
                case NORMAL:
                    boxSetpointInTicks = convertMetersToTicks(getAverageEncoderMeters() +BOX_MOTOR_SLACK);
            }

            setLiftPower(leftPID.PIDControl(liftSetpointInTicks,liftMotorLeft.getCurrentPosition()),
                    rightPID.PIDControl(liftSetpointInTicks,liftMotorRight.getCurrentPosition())
            );
            setBoxMotorPower(boxTiltPID.PIDControl(boxSetpointInTicks,boxTiltMotor.getCurrentPosition()));
        } else {
            setLiftPower(0,0);
            setBoxMotorPower(0);
        }


    }

    private void hangRobot() {
        setLiftPower(0, 0);
        advanceGoal(ROBOT_HANG_HEIGHT);
        liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
        setBoxMotorPower(boxTiltPID.PIDControl(boxSetpointInTicks, boxTiltMotor.getCurrentPosition()));
    }


    public static synchronized LinearSlide getInstance(){
        if(instance == null){
            instance = new LinearSlide();
        }
        return instance;
    }




    // Encoder Methods
    public double convertTicksToMeters(double ticks){
        return (((ticks / HD_HEX_TICKS_PER_REVOLUTION) / MOTORS_REDUCTION) * PULLEY_CIRCUMFERENCE);
    }
    public double convertMetersToTicks(double meters){
        return (((meters * HD_HEX_TICKS_PER_REVOLUTION) * MOTORS_REDUCTION) / PULLEY_CIRCUMFERENCE);
    }

    public double getAverageEncoderTicks(){
        return ((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }

    public double getAverageEncoderMeters(){
        return convertTicksToMeters((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }
    private void advanceGoal(double setpoint){

        if (currentPIDGoal+GOAl_STEP>setpoint&&currentPIDGoal-GOAl_STEP<setpoint){
            currentPIDGoal =setpoint;
        } else{
            if (currentPIDGoal>setpoint){
                currentPIDGoal -= GOAl_STEP;
            } else if (currentPIDGoal<setpoint){
                currentPIDGoal += GOAl_STEP;
            }
        }
    }
}