package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.hardwareMap.RobotHardware;

public class Drivetrain{
    private final DcMotor motorFrontRight;
    private final DcMotor motorFrontLeft;
    private final DcMotor motorBackRight;
    private final DcMotor motorBackLeft;

    public Drivetrain(){
        motorFrontRight = RobotHardware.Drivetrain.motorFrontRight;
        motorFrontLeft= RobotHardware.Drivetrain.motorFrontRight;
        motorBackLeft= RobotHardware.Drivetrain.motorFrontRight;
        motorBackRight = RobotHardware.Drivetrain.motorFrontRight;
    }

    public void stop(){
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
    }
}
