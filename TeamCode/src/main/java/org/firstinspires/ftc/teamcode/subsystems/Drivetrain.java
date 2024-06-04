package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.constants.DrivetrainConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.ButtonListener;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class Drivetrain implements Subsystem {

    private static Drivetrain instance;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private IMU imu;

    private StaticHeading pidController;

    private double triggerDeadBand = 0.3;

    private Drivetrain() {
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {

        motorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT);
        motorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT);
        imu = hardwareMap.get(IMU.class, IMU);

        pidController = new StaticHeading(0.5, 0.01, 0.0, 0.3);
        pidController.setTolerance(0.05);

        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("DriveTrain Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController driver, Telemetry telemetry) {
        telemetry.addData("DriveTrain Subsystem", "Running");

        arcadeDrive(-driver.getLeftStickY(), driver.getRightStickX(), driver.getLeftTrigger(), driver.getRightTrigger());

        ButtonListener.whileTrue(driver.isButtonA())
                .run(() -> setPosition(1.16, getAverageEncoderMeters()));
        ButtonListener.toggleOnTrue(driver.isButtonB())
                .run(() -> resetEncoders());

        if (pidController.atSetPoint()) {
            telemetry.addData("pid", "atSetpoint");
        }
        telemetry.addData("encoder", getAverageEncoderMeters());

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    public void setPosition(double setPoint, double reference) {
        setPower(pidController.calculate(setPoint, reference), pidController.calculate(setPoint, reference));

    }

    public void arcadeDrive(double xSpeed, double zRotation, double slowMode, double fastMode) {
        double limiter = 0.8;
        if (slowMode > triggerDeadBand &&  fastMode < triggerDeadBand){
            limiter = 0.6;
        } else if(fastMode > triggerDeadBand && slowMode < triggerDeadBand) {
            limiter = 1.0;
        }

        double xSpeedLimited = Math.max(-limiter, Math.min(limiter, xSpeed));
        double zRotationLimited = Math.max(-limiter, Math.min(limiter, zRotation));

        double leftSpeed = xSpeedLimited - zRotationLimited;
        double rightSpeed = xSpeedLimited + zRotationLimited;

        setPower(leftSpeed, rightSpeed);
    }

    public void setPower(double leftSpeed, double rightSpeed) {
        motorLeft.setPower(leftSpeed);
        motorRight.setPower(rightSpeed);
    }

    // Encoder Methods
    public void resetEncoders(){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public double convertTicksToMeters(double ticks) {
        return ((ticks / HD_HEX_TICKS_PER_REVOLUTION * MOTORS_REDUCTION) * WHEEL_CIRCUMFERENCE);
    }

    public double convertMetersToTicks(double meters) {
        return (((meters * HD_HEX_TICKS_PER_REVOLUTION) * MOTORS_REDUCTION) / WHEEL_CIRCUMFERENCE);
    }

    public double getAverageEncoderTicks() {
        return ((double) (motorLeft.getCurrentPosition() + motorRight.getCurrentPosition()) / 2);
    }

    public double getAverageEncoderMeters() {
        return convertTicksToMeters((double) (motorLeft.getCurrentPosition() + motorRight.getCurrentPosition()) / 2);
    }

    public static synchronized Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }
}
