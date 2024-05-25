package org.firstinspires.ftc.teamcode.util;

public class PIDF {

    public double error=0;

    public double kP;

    public double kI;
    public double maxkI;
    private double cummulativeI=0;

    private double prevError=0;
    public double kD;
    public double kF;
    private double currentError;

    public PIDF(double newKP, double newKI, double newKD, double newKF, double newMaxKI){
        kP = newKP;
        kI = newKI;
        maxkI =newMaxKI;
        kD = newKD;
        kF = newKF;
    }

    public double P(double error){
        return error*kP;
    }
    public double I(double error){
        if (Math.abs(error*kI+cummulativeI)<maxkI){
            cummulativeI += error*kI;
            return cummulativeI;
        } else {
            if (cummulativeI>0){
                cummulativeI = maxkI;
                return cummulativeI;
            } else{
                cummulativeI = -maxkI;
                return cummulativeI;
            }
        }
    }

    public double D(double error, double elapsedTime){
        return (error-prevError)/elapsedTime;
    }

    public double calculatePIDF(double setpoint,double position,double elapsedTime){
        currentError = position-setpoint;
        double value = P(currentError)+I(currentError)+D(currentError,elapsedTime)+kF;
        prevError = currentError;
        return value;
    }


}
