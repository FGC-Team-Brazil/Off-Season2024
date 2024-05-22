package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Drivetrain{

    private static Drivetrain instance;
    private static DcMotor motorRight;
    private static DcMotor motorLeft;
    private IMU imu;

    private Drivetrain(){
        imu = RobotHardware.Drivetrain.imu;
        motorRight = RobotHardware.Drivetrain.motorRight;
        motorLeft= RobotHardware.Drivetrain.motorLeft;
    }

    public void arcadeDrive(double xSpeed, double zRotation){
        xSpeed = Math.max(-1.0, Math.min(1.0, xSpeed));
        zRotation = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeed - zRotation;
        double rightSpeed = xSpeed + zRotation;

        setSpeed(leftSpeed, rightSpeed);
    }

    public void setSpeed(double leftSpeed, double rightSpeed){
        motorLeft.setPower(leftSpeed);
        motorRight.setPower(rightSpeed);
    }
    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }
    public static synchronized Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }
}
