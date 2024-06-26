package org.firstinspires.ftc.teamcode.util;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Standard class for building PIDF control systems.
 * It uses the constants: Proportional, Integral and Derivative.
 * In addition to PID, it implements a FeedForward constant.
 */
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

    private double setPoint;
    private boolean atSetPoint;
    private double positionTolerance = 0;

    private double output = 0;
    private Mode actualMode;

    private ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    private int revolutionEncoder = 0;

    public StaticHeading(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.actualMode = Mode.POSITION;
    }

    public StaticHeading(double kP, double kI, double kD, double kF, Mode mode) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.actualMode = mode;
    }

    public double calculate(double setPoint, double realPosition) {
        this.setPoint = setPoint;
        double error = 0;

        if (actualMode == Mode.ANGLE) {
            error = angleWrap(setPoint - realPosition);
        } else {
            error = setPoint - realPosition;
        }

        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * kP) + (derivative * kD) + (integralSum * kI) + kF;

        if (Math.abs(error) < positionTolerance) {
            atSetPoint = true;
            output = 0;
        } else {
            this.output = output;
        }
        return output;
    }

    public void setPowerMotor(DcMotor dcMotor, int revolutionEncoder) {
        int conversionValue = this.revolutionEncoder / 360;
        dcMotor.setPower(output * conversionValue);
    }

    private double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

    // Set Methods
    public void setKP(double newKP) {
        this.kP = newKP;
    }

    public void setKI(double newKI) {
        this.kI = newKI;
    }

    public void setKD(double newKD) {
        this.kD = newKD;
    }

    public void setKF(double newKF) {
        this.kF = newKF;
    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void setTolerance(double positionTolerance) {
        this.positionTolerance = Math.abs(positionTolerance);
    }

    // Get Methods
    public double getKP(double newKP) {
        return this.kP;
    }

    public double getKI(double newKI) {
        return this.kI;
    }

    public double getKD(double newKD) {
        return this.kD;
    }

    public double getKF(double newKF) {
        return this.kF;
    }

    public boolean atSetPoint() {
        return atSetPoint;
    }
}