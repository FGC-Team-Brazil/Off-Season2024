package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.arcadeDrive;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Drivetrain{
    private final DcMotor motorRight;
    private final DcMotor motorLeft;

    public Drivetrain(){
        motorRight = RobotHardware.DTmotorRight;
        motorLeft= RobotHardware.DTmotorLeft;
    }

    public static void arcadeDrive(double xSpeed, double zRotation){
        xSpeed = Math.max(-1.0, Math.min(1.0, xSpeed));
        zRotation = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeed - zRotation;
        double rightSpeed = xSpeed + zRotation;

        RobotHardware.setDrivetrainPower(leftSpeed, rightSpeed);
    }

    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }
}
