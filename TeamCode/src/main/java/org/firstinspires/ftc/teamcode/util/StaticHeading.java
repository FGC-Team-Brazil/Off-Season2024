package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Static Heading")
public class StaticHeading {
    public enum Mode {
        POSITION,
        ANGLE,
    }
   private double kP;
    private double kI;
    private double kD;
    private double kF;

    private double integralSum = 0;
    private double setPoint = 0;
    private double output =0;
    private Mode actualMode;

    private ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    private int revolutionEncoder = 0;

    public StaticHeading(double kP, double kI, double kD, double kF){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.actualMode = Mode.POSITION;
    }

    public StaticHeading(double kP, double kI, double kD, double kF, Mode mode){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.actualMode = mode;
    }

    public double PIDControl(double setPoint, double realPosition) {
        double error = 0;

        if(actualMode == Mode.ANGLE){
            error = angleWrap(setPoint - realPosition);
        }else{
            error = setPoint - realPosition;
        }

        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * kP) + (derivative * kD) + (integralSum * kI) + kF;
        this.output = output;
        return output;
    }

    public void setPowerMotor(DcMotor dcMotor,int revolutionEncoder){
        int conversionValue = this.revolutionEncoder / 360;
        dcMotor.setPower(output * conversionValue);
    }

    private double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }
}