package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Drivetrain{
    private final DcMotor motorFrontRight;
    private final DcMotor motorFrontLeft;
    private final DcMotor motorBackRight;
    private final DcMotor motorBackLeft;

    public Drivetrain(){
        motorFrontRight = RobotHardware.motorFrontRight;
        motorFrontLeft= RobotHardware.motorfrontLeft;
        motorBackLeft= RobotHardware.motorbackLeft;
        motorBackRight = RobotHardware.motorbackRight;
    }

    public void stop(){
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
    }
}
