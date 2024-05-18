package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class RobotHardware {
    public static IMU imu;
    public static DcMotor motorFrontRight;
    public static DcMotor motorbackRight;
    public static DcMotor motorfrontLeft;
    public static DcMotor motorbackLeft;
    static HardwareMap hardwareMap;



    public static void setHardwareMap(HardwareMap map){
        hardwareMap = map;
    }
    public static void initAll() {
        imu = hardwareMap.get(IMU.class, "imu");
        motorFrontRight = hardwareMap.get(DcMotor.class, "FRmotor");
        motorbackRight = hardwareMap.get(DcMotor.class, "BRmotor");
        motorfrontLeft = hardwareMap.get(DcMotor.class, "FLmotor");
        motorbackLeft = hardwareMap.get(DcMotor.class, "BLmotor");
    }



}
