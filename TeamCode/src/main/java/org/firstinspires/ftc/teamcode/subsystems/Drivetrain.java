package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Drivetrain{
    private final DcMotor motorRight;
    private final DcMotor motorLeft;
    private final IMU imu;
    public Drivetrain(){
        imu = RobotHardware.DTimu;
        motorRight = RobotHardware.DTmotorRight;
        motorLeft= RobotHardware.DTmotorLeft;
    }

    public static void arcadeDrive(double xSpeed, double zRotation){
        double xSpeedLimited = Math.max(-1.0, Math.min(1.0, xSpeed));
        double zRotationLimited = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeedLimited - zRotationLimited;
        double rightSpeed = xSpeedLimited + zRotationLimited;

        RobotHardware.setDrivetrainPower(leftSpeed, rightSpeed);
    }

    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }
}
