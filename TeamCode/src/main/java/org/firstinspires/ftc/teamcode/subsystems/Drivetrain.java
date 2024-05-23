package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotHardwareConstants;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private IMU imu;

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
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        motorLeft = hardwareMap.get(DcMotor.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.MOTOR_LEFT);
        motorRight = hardwareMap.get(DcMotor.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.MOTOR_RIGHT);
        imu = hardwareMap.get(IMU.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.IMU);
    }

    @Override
    public void stop(){
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    public void setPower(double leftSpeed, double rightSpeed) {
        motorLeft.setPower(leftSpeed);
        motorRight.setPower(rightSpeed);
    }

    public static synchronized Drivetrain getInstance(){
        if(instance == null){
            instance = new Drivetrain();
        }
        return instance;
    }
}
