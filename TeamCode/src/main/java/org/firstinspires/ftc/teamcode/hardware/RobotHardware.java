package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

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
        public static IMU imu;
        public static DcMotor motorRight;
        public static DcMotor motorLeft;

        public static void init(){
            motorRight = hardwareMap.get(DcMotor.class, "Rmotor");
            motorLeft = hardwareMap.get(DcMotor.class, "Lmotor");

        }
    }

}
