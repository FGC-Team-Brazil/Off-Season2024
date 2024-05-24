package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotHardwareConstants;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.SmartController;

public class Drivetrain implements Subsystem {
    private static Drivetrain instance;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private IMU imu;

    private Drivetrain(){
    }

    @Override
    public void execute(SmartController driver) {
        arcadeDrive(-driver.getLeftStickY(), driver.getRightStickX());

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

    public void arcadeDrive(double xSpeed, double zRotation){
        double xSpeedLimited = Math.max(-1.0, Math.min(1.0, xSpeed));
        double zRotationLimited = Math.max(-1.0, Math.min(1.0, zRotation));

        double leftSpeed = xSpeedLimited - zRotationLimited;
        double rightSpeed = xSpeedLimited + zRotationLimited;

        setPower(leftSpeed, rightSpeed);
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
