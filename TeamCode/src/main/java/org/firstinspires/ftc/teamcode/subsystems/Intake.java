package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class Intake implements Subsystem {

    private static Intake instance;
    private TouchSensor limitRight;
    private TouchSensor limitLeft;
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private StaticHeading pidController;
    private final int TARGET_DEGREE = 110;
    private  final int REVOLUTION_ENCODER = 288;
    private Intake (){
    }

    @Override
    public void execute(SmartController operator) {

        if(operator.getButtonLeftBumper()){
            motorRight.setPower(0);
            motorLeft.setPower(0.5);
        } else if(operator.getButtonRightBumper()){
            motorRight.setPower(0.5);
            motorLeft.setPower(0);
        } else if (!operator.getButtonLeftBumper()) {
            motorLeft.setPower(-0.5);
        } else if (!operator.getButtonRightBumper()) {
            motorRight.setPower(-0.5);
        }
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        limitRight = hardwareMap.get(TouchSensor.class, IntakeConstants.LIMIT_RIGHT);
        limitLeft = hardwareMap.get(TouchSensor.class, IntakeConstants.LIMIT_LEFT);

        motorRight = hardwareMap.get(DcMotor.class, IntakeConstants.MOTOR_RIGHT);
        motorLeft = hardwareMap.get(DcMotor.class, IntakeConstants.MOTOR_LEFT);

        pidController = new StaticHeading();

        pidController.PIDControl(TARGET_DEGREE, REVOLUTION_ENCODER, true);

        pidController.setPowerMotor(motorRight, REVOLUTION_ENCODER);
        pidController.setPowerMotor(motorLeft, REVOLUTION_ENCODER);

    }

    @Override
    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    public boolean isLimitRight(){
        return limitRight.isPressed();
    }

    public boolean isLimitLeft(){
        return limitLeft.isPressed();
    }

    public static synchronized Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }

}
