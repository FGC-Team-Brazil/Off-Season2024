package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

import static org.firstinspires.ftc.teamcode.constants.LinearSlideConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;
import org.firstinspires.ftc.teamcode.util.SmartController;

public class LinearSlide implements Subsystem {
    private static LinearSlide instance;

    private DcMotor liftMotorRight;
    private DcMotor liftMotorLeft;

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        liftMotorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        liftMotorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);

        telemetry.addData("LinearSlide Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController operator) {
        setPower(operator.getLeftStickY(), operator.getLeftStickY());
        if (operator.getButtonA() != operator.getButtonY()) {
            while(operator.getButtonY()) {
                /*goToHeight(heightLv3, );*/
            }
            while(operator.getButtonA()) {
                /*goToHeight(heightLv2, );*/
            }
        }
    }

    @Override
    public void stop(){
        liftMotorRight.setPower(0);
        liftMotorLeft.setPower(0);
    }

    private void setPower(double leftPower, double rightPower){
        liftMotorLeft.setPower(leftPower);
        liftMotorRight.setPower(rightPower);
    }

    // Command Methods
    private void goToHeight(double setpoint, double state){
        // PID to set the slide position in meters with the encoders
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

    public double getAverageEncoderTicks(){
        return ((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }

    public double getAverageEncoderMeters(){
        return convertTicksToMeters((double) (liftMotorLeft.getCurrentPosition() + liftMotorRight.getCurrentPosition()) / 2);
    }
}