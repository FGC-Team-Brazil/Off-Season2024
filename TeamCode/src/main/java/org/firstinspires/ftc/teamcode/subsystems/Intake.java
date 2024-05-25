package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.SmartController;

public class Intake implements Subsystem {

    private static Intake instance;
    private TouchSensor limitRight;
    private TouchSensor limitLeft;
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private final int TARGET_DEGREE = 110;
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

        motorRight.setTargetPosition(TARGET_DEGREE);
        motorLeft.setTargetPosition(TARGET_DEGREE);

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
