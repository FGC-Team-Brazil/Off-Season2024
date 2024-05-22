package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.Drivetrain.*;

public class Drivetrain{
    private static Drivetrain instance;

    private Drivetrain(){

    }

    public static void arcadeDrive(double xSpeed, double zRotation){
        double xSpeedLimited = Math.max(-1.0, Math.min(1.0, xSpeed));
        double zRotationLimited = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeedLimited - zRotationLimited;
        double rightSpeed = xSpeedLimited + zRotationLimited;

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
