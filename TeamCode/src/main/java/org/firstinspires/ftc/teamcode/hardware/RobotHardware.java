package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.constants.RobotHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class RobotHardware {
    private static HardwareMap hardwareMap;

    public static void setHardwareMap(HardwareMap map){
        hardwareMap = map;
    }
    public static void init() {
        Drivetrain.init();
    }

    public static class Drivetrain {
        public static IMU IMU;
        public static DcMotor MOTOR_RIGHT;
        public static DcMotor MOTOR_LEFT;

        public static void init(){
            MOTOR_RIGHT = hardwareMap.get(DcMotor.class, RobotHardwareConstants.Drivetrain.MOTOR_RIGHT);
            MOTOR_LEFT = hardwareMap.get(DcMotor.class, RobotHardwareConstants.Drivetrain.MOTOR_LEFT);
            IMU = hardwareMap.get(IMU.class, RobotHardwareConstants.Drivetrain.IMU);
        }
    }

}
