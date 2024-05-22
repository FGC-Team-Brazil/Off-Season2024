package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.Drivetrain.*;

public class Drivetrain{
    private static Drivetrain instance;

    private Drivetrain(){

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
