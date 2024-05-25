package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

import static org.firstinspires.ftc.teamcode.constants.LinearSlideConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.teamcode.util.PIDF;
import org.firstinspires.ftc.teamcode.util.SmartController;

public class LinearSlide implements Subsystem {
    private static LinearSlide instance;

    public double currentPIDGoal=0;
    private DcMotor liftMotorRight;
    private DcMotor liftMotorLeft;
    private DcMotor boxTiltMotor;


    PIDF leftPID;
    PIDF rightPID;
    PIDF boxTiltPID;
    private double previousRunTime=0;


    public enum linearMode{
        NORMAL,
        DEPOSIT,
        HANG
    }
    private double endHeightGoal =0;
    private double liftSetpointInTicks=0;
    private double boxSetpointInTicks =0;
    private ElapsedTime runtime = new ElapsedTime();
    public linearMode currentMode = linearMode.NORMAL;

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        liftMotorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        liftMotorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);
        boxTiltMotor = hardwareMap.get(DcMotor.class,MOTOR_BOX);
        leftPID = new PIDF(0,0,0,0,0);
        rightPID = new PIDF(0,0,0,0,0);
        boxTiltPID = new PIDF(0,0,0,0,0);

        telemetry.addData("LinearSlide Subsystem", "Initialized");
    }
    @Override
    public void start(){
        runtime.reset();

    }
    @Override
    public void execute(SmartController operator) {

        double elapsedTime = runtime.seconds() - previousRunTime;

        if (operator.getButtonA()){
            endHeightGoal= DEPOSIT_HEIGHT_MID;
        } else if (operator.getButtonY()){
            endHeightGoal = DEPOSIT_HEIGHT_HIGH;
        }

        if (endHeightGoal== DEPOSIT_HEIGHT_MID &&operator.getRightTrigger()>0.9){
            currentMode=linearMode.HANG;
        }

        boolean physicalLimitReached = false||false;//todo replace false's with sensor detecting slide limit conditions

        switch (currentMode){
            case NORMAL:
            case DEPOSIT:
                goToHeight(endHeightGoal, physicalLimitReached,elapsedTime);
                break;
            case HANG:
                hangRobot(elapsedTime);
                break;
        }
        previousRunTime=runtime.seconds();

    }

    @Override
    public void stop(){
        liftMotorRight.setPower(0);
        liftMotorLeft.setPower(0);

    }

    private void setLiftPower(double leftPower, double rightPower){
        liftMotorLeft.setPower(leftPower);
        liftMotorRight.setPower(rightPower);
    }
    private void setBoxMotorPower(double power){
        boxTiltMotor.setPower(power);
    }

    // Command Methods
    private void goToHeight(double setpoint, boolean forceStop,double elapsedTime){
        if (!forceStop) {
            advanceGoal(setpoint);
            liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
            switch (currentMode){
                case DEPOSIT:
                    boxSetpointInTicks = convertMetersToTicks(getAverageEncoderMeters()+BOX_MOTOR_DEPOSIT_CONTRACTION);
                case NORMAL:
                    boxSetpointInTicks = convertMetersToTicks(getAverageEncoderMeters() +BOX_MOTOR_SLACK);
            }

            setLiftPower(leftPID.calculatePIDF(liftSetpointInTicks,liftMotorLeft.getCurrentPosition(),elapsedTime),
                    rightPID.calculatePIDF(liftSetpointInTicks,liftMotorRight.getCurrentPosition(),elapsedTime)
            );
            setBoxMotorPower(boxTiltPID.calculatePIDF(boxSetpointInTicks,boxTiltMotor.getCurrentPosition(),elapsedTime));
        } else {
            setLiftPower(0,0);
            setBoxMotorPower(0);
        }


    }

    private void hangRobot(double elapsedTime){
        setLiftPower(0,0);
        advanceGoal(ROBOT_HANG_HEIGHT);
        liftSetpointInTicks = convertMetersToTicks(currentPIDGoal);
        setBoxMotorPower(boxTiltPID.calculatePIDF(boxSetpointInTicks,boxTiltMotor.getCurrentPosition(),elapsedTime));
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