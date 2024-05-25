package org.firstinspires.ftc.teamcode.constants;

public class LinearSlideConstants {

    public static final String MOTOR_RIGHT = "SlideRmotor";
    public static final String MOTOR_LEFT = "SlideLmotor";
    public static final String MOTOR_BOX = "BoxMotor";//todo put actual name here
    public static final double MOTORS_REDUCTION = (float) 1/36;
    public static final double PULLEY_CIRCUMFERENCE = 2 * 0.03 * Math.PI;
    public static final double SLIDE_MAX_HEIGHT = 2.5; //This value is a placeholder
    public static final double GOAl_STEP =1; //goal step is used to control the target of the height pids so it doesn't change all at once
    public static final double BOX_MOTOR_SLACK = 0;//amount in meters for the slack in the box tilt motor when not depositing
    public static final double BOX_MOTOR_DEPOSIT_CONTRACTION = 0;//amount in meters the box motor will retract the pid when tilting the box
    public static final double DEPOSIT_HEIGHT_HIGH =0;
    public static final double DEPOSIT_HEIGHT_MID =0;
    public static final double ROBOT_HANG_HEIGHT =0;

    public static final double kP = 0; //This value is a placeholder
    public static final double kI = 0; //This value is a placeholder
    public static final double kD = 0; //This value is a placeholder
    public static final double kF = 0; //This value is a placeholder
}
