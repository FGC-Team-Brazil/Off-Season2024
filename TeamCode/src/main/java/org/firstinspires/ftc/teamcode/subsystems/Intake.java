package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.util.StaticHeading.Mode.ANGLE;
import static org.firstinspires.ftc.teamcode.constants.IntakeConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class Intake implements Subsystem {

    private static Intake instance;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private TouchSensor limitRight;
    private TouchSensor limitLeft;

    private StaticHeading pidController;

    private Intake (){}

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
        if(operator.isButtonLeftBumper()){
            // If the left bumper is pressed, the left intake will open
            motorRight.setPower(0);
            pidController.setPowerMotor(motorLeft, (int) CORE_HEX_TICKS_PER_REVOLUTION);
        } else if(operator.isButtonRightBumper()){
            // If the right bumper is pressed, the right intake will open
            pidController.setPowerMotor(motorRight, (int) CORE_HEX_TICKS_PER_REVOLUTION);
            motorLeft.setPower(0);
        } else if (operator.isLeftTriggerPressed() && !isLimitLeft()) {
            // If the left trigger is pressed, the left intake will close
            motorLeft.setPower(operator.getLeftTrigger());
        } else if(operator.isLeftTriggerPressed() && isLimitLeft()){
            // If the left limit is pressed, the left encoder will reset
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            operator.rumble(1,0, 200);
        } else if (operator.isRightTriggerPressed() && !isLimitRight()) {
            // If the right trigger is pressed, the right intake will close
            motorRight.setPower(operator.getRightTrigger());
        } else if(operator.isRightTriggerPressed() && isLimitRight()){
            // If the right limit is pressed, the right encoder will reset
            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            operator.rumble(0,1, 200);
        } else if (operator.isButtonLeftBumper() && operator.isButtonRightBumper()){
            // If both bumpers are pressed, the intake will open
            pidController.setPowerMotor(motorLeft, (int) CORE_HEX_TICKS_PER_REVOLUTION);
            pidController.setPowerMotor(motorRight, (int) CORE_HEX_TICKS_PER_REVOLUTION);
        } else if(operator.isRightTriggerPressed() && !isLimitRight() && operator.isLeftTriggerPressed() && !isLimitLeft()){
            // If both triggers are pressed, the intake will close
            motorRight.setPower(operator.getRightTrigger());
            motorLeft.setPower(operator.getLeftTrigger());
        } else {
            stop();
        }
    }
    @Override
    public void start(){

    }

    @Override
    public void stop() {
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }


    // Sensor Methods
    public boolean isLimitRight() {return limitRight.isPressed();}
    public boolean isLimitLeft() {return limitLeft.isPressed();}

    // Singleton Instance
    public static synchronized Intake getInstance() {
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }
}