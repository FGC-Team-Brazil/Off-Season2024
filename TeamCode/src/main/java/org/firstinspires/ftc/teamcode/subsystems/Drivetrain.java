package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Drivetrain{

    private static Drivetrain instance;
    private static DcMotor MOTOR_RIGHT;
    private static DcMotor MOTOR_LEFT;
    private IMU imu;

    private Drivetrain(){
        imu = RobotHardware.Drivetrain.IMU;
        MOTOR_RIGHT = RobotHardware.Drivetrain.MOTOR_RIGHT;
        MOTOR_LEFT = RobotHardware.Drivetrain.MOTOR_LEFT;
    }

    public void arcadeDrive(double xSpeed, double zRotation){
        xSpeed = Math.max(-1.0, Math.min(1.0, xSpeed));
        zRotation = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeed - zRotation;
        double rightSpeed = xSpeed + zRotation;

        setSpeed(leftSpeed, rightSpeed);
    }

    public void setSpeed(double leftSpeed, double rightSpeed){
        MOTOR_LEFT.setPower(leftSpeed);
        MOTOR_RIGHT.setPower(rightSpeed);
    }
    public void stop(){
        MOTOR_RIGHT.setPower(0);
        MOTOR_LEFT.setPower(0);
    }
    public static synchronized Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }
}
