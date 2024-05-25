package org.firstinspires.ftc.teamcode.constants;

public class IntakeConstants {
    public static final String MOTOR_LEFT = "intake_motorLeft";
    public static final String MOTOR_RIGHT = "intake_motorRight";
    public static final String LIMIT_LEFT = "intake_limitLeft";
    public static final String LIMIT_RIGHT = "intake_limitRight";

    public static class PID {
        public static final double kP = 1.8;
        public static final double kI = 0.0;
        public static final double kD = 0.031;
        public static final double kF = 0.1;
    }
}
