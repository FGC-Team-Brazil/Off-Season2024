package org.firstinspires.ftc.teamcode.util;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import org.firstinspires.ftc.teamcode.constants.PIDConstants;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Static Heading")
public class StaticHeading extends LinearOpMode {
    double integralSum = 0;
    double Kp = PIDConstants.kp;
    double Ki = PIDConstants.ki;
    double Kd = PIDConstants.kd;
    double kf = PIDConstants.kf;

    public double setPoint = 0;

    Intake intake = new Intake();

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    public void PIDSetPoint(double setPoint){
        this.setPoint = setPoint;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        double refrenceAngle = Math.toRadians(setPoint);
        waitForStart();

        while(opModeIsActive()){
            telemetry.update();
        }
        PIDControl(refrenceAngle,1);

    }

    public double PIDControl(double refrence, double state) {
        double error = angleWrap(refrence - state);
        telemetry.addData("Error: ", error);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki) + kf;
        return output;
    }
    public double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }


}