package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.constants.RobotHardwareConstants;

public class RobotHardware {
    private static HardwareMap hardwareMap;

    public static void setHardwareMap(HardwareMap map){
        hardwareMap = map;
    }
    public static void init() {
        DrivetrainHardwareMap.motorLeft = hardwareMap.get(DcMotor.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.MOTOR_LEFT);
        DrivetrainHardwareMap.motorRight = hardwareMap.get(DcMotor.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.MOTOR_RIGHT);
        DrivetrainHardwareMap.imu = hardwareMap.get(IMU.class, RobotHardwareConstants.DrivetrainHardwareMapConstants.IMU);
    }

    public static class DrivetrainHardwareMap {
        public static IMU imu;
        public static DcMotor motorRight;
        public static DcMotor motorLeft;
    }

}
