package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.constants.XDrivetrainConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.util.ButtonListener;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

import java.util.ArrayList;

public class XDrivetrain implements Subsystem {

    private static XDrivetrain instance;

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private IMU imu;
    private ArrayList<DcMotor> driveMotors;

    private StaticHeading pidController;
    
    Telemetry telemetry;

    private XDrivetrain() {
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {

        frontLeftMotor = hardwareMap.get(DcMotor.class, FL_MOTOR);
        backLeftMotor = hardwareMap.get(DcMotor.class, BL_MOTOR);
        frontRightMotor = hardwareMap.get(DcMotor.class, FR_MOTOR);
        backRightMotor = hardwareMap.get(DcMotor.class, BR_MOTOR);
        imu = hardwareMap.get(BHI260IMU.class, IMU);
        imu.initialize(
            new IMU.Parameters(
                new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
            )
        );
        driveMotors = new ArrayList<DcMotor>();
        
        this.telemetry = telemetry;

        pidController = new StaticHeading(0.5, 0.01, 0.0, 0.3);
        pidController.setTolerance(0.05);

        driveMotors.add(frontLeftMotor);
        driveMotors.add(backLeftMotor);
        driveMotors.add(frontRightMotor);
        driveMotors.add(backRightMotor);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        driveMotors.forEach(motor -> motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER));
        driveMotors.forEach(motor -> motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER));

        telemetry.addData("XDriveTrain Subsystem", "Initialized");
    }

    @Override
    public void execute(SmartController driver) {
        telemetry.addData("XDriveTrain Subsystem", "Running");

        arcadeDrive(driver);

        if (pidController.atSetPoint()) {
            telemetry.addData("pid", "atSetpoint");
        }

    }

    @Override
    public void start() {
        resetHeading();
        resetEncoders();
    }

    @Override
    public void stop() {
        driveMotors.forEach(motor -> motor.setPower(0));
    }


    public void arcadeDrive(SmartController driver) {
        double protate = driver.getRightStickX()/4;
        double limiter = Math.sqrt(Math.pow(1-Math.abs(protate), 2)/2); //Accounts for Protate when limiting magnitude to be less than 1
        double leftStickX = driver.getLeftStickX() * limiter;
        double leftStickY = driver.getLeftStickY() * limiter;
        double theta = 0;
        double Px = 0;
        double Py = 0;

        
        double gyroAngle = getHeading() * Math.PI / 180; // Converts gyroAngle into radians
        if (gyroAngle <= 0) {
            gyroAngle = gyroAngle + (Math.PI / 2);
        } else if (0 < gyroAngle && gyroAngle < Math.PI / 2) {
            gyroAngle = gyroAngle + (Math.PI / 2);
        } else if (Math.PI / 2 <= gyroAngle) {
            gyroAngle = gyroAngle - (3 * Math.PI / 2);
        }
        gyroAngle = -1 * gyroAngle;

        // DPad for Linear Directions
        if(driver.isButtonDPadRight()){
            leftStickX = 1;
        }
        else if(driver.isButtonDPadLeft()){
            leftStickX = -1;
        }
        if(driver.isButtonDPadUp()){
            leftStickY = -1;
        }
        else if(driver.isButtonDPadDown()){
            leftStickY = 1;
        }

        // MOVEMENT
        theta = Math.atan2(leftStickY, leftStickX) - gyroAngle - (Math.PI / 2); //PEGA ANGULO DO CIRCULO TRIGONOMÉTRICO, ENTRE X E Y (HÁ A CORREÇÃO DA ORIENTAÇÃO AQUI)
        Px = Math.sqrt(Math.pow(leftStickX, 2) + Math.pow(leftStickY, 2)) * (Math.sin(theta + Math.PI / 4)); //RODAS FRONT RIGHT E BACK LEFT // AQUI ELE SOMA 45GRAUS POIS AS RODAS PARES TEM DIFERENÇA DE 90GRAUS
        Py = Math.sqrt(Math.pow(leftStickX, 2) + Math.pow(leftStickY, 2)) * (Math.sin(theta - Math.PI / 4)); //RODAS FRONT LEFT E BACK RIGHT // AQUI ELE DIMINUI 45GRAUS POIS AS RODAS PARES TEM DIFERENÇA DE 90GRAUS

        frontLeftMotor.setPower(Py - protate);
        backLeftMotor.setPower(Px - protate);
        backRightMotor.setPower(Py + protate);
        frontRightMotor.setPower(Px + protate);
    }

    // Gyro Methods
    public void resetHeading(){
        imu.resetYaw();
    }
    public double getHeading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    // Encoder Methods
    public void resetEncoders(){
        driveMotors.forEach(motor -> {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        });
    }
    public double convertTicksToMeters(double ticks) {
        return ((ticks / HD_HEX_TICKS_PER_REVOLUTION * MOTORS_REDUCTION) * WHEEL_CIRCUMFERENCE);
    }

    public double convertMetersToTicks(double meters) {
        return (((meters * HD_HEX_TICKS_PER_REVOLUTION) * MOTORS_REDUCTION) / WHEEL_CIRCUMFERENCE);
    }

    public static synchronized XDrivetrain getInstance() {
        if (instance == null) {
            instance = new XDrivetrain();
        }
        return instance;
    }
}
