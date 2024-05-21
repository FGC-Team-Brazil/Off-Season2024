package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class RobotHardware {
    public static  DcMotor DTmotorLeft;
    public static DcMotor DTmotorRight;
    public static IMU DTimu;
    static HardwareMap hardwareMap;



    public static void setHardwareMap(HardwareMap map){
        hardwareMap = map;
    }
    public static void initAll(HardwareMap hardwareMap) {
        DTmotorLeft = RobotHardware.hardwareMap.get(DcMotor.class, "DTmotorLeft");
        DTmotorRight = RobotHardware.hardwareMap.get(DcMotor.class, "DTmotorRight");
        DTimu = RobotHardware.hardwareMap.get(IMU.class, "DTImu");
    }
    public static void setDrivetrainPower(double   leftPower, double rightPower){
        DTmotorLeft.setPower(leftPower);
        DTmotorRight.setPower(rightPower);
    }


}
