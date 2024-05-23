package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.DrivetrainHardwareMap.*;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;

    private Drivetrain(){

    }
    public void arcadeDrive(double xSpeed, double zRotation){
        double xSpeedLimited = Math.max(-1.0, Math.min(1.0, xSpeed));
        double zRotationLimited = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeedLimited - zRotationLimited;
        double rightSpeed = xSpeedLimited + zRotationLimited;

        instance.setPower(leftSpeed, rightSpeed);
    }

    @Override
    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    @Override
    public void setPower(double... powers) {
        motorLeft.setPower(powers[0]);
        motorRight.setPower(powers[1]);
    }

    public static synchronized Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }
}
