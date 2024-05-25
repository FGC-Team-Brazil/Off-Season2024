package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.constants.PIDConstants;

@TeleOp(name = "Static Heading")
public class StaticHeading {

    double integralSum = 0;
    double Kp = PIDConstants.kp;
    double Ki = PIDConstants.ki;
    double Kd = PIDConstants.kd;
    double kf = PIDConstants.kf;

    public double setPoint = 0;
    public double output =0;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    //
    public double PIDControl(double setPoint, double realPosition,boolean angular) {
        double error = 0;

        if(angular){
            error = angleWrap(setPoint - realPosition);
        }else{
            error = setPoint - realPosition;
        }

        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki) + kf;
        this.output = output;
        return output;
    }

    public void setPowerMotor(DcMotor dcMotor){
        dcMotor.setPower(output);
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