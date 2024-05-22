package org.firstinspires.ftc.teamcode.hardware.hardwareMap;

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
        public static DcMotor motorFrontRight;
        public static DcMotor motorBackRight;
        public static DcMotor motorFrontLeft;
        public static DcMotor motorBackLeft;

        public static void init(){
            motorFrontRight = hardwareMap.get(DcMotor.class, "FRmotor");
            motorBackRight = hardwareMap.get(DcMotor.class, "BRmotor");
            motorFrontLeft = hardwareMap.get(DcMotor.class, "FLmotor");
            motorBackLeft = hardwareMap.get(DcMotor.class, "BLmotor");
        }
    }

}
