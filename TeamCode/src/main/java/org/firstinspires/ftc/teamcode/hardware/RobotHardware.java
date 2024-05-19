package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class RobotHardware {
    public static  DcMotor DTmotorLeft;
    public static DcMotor DTmotorRight;
    static HardwareMap hardwareMap;



    public static void setHardwareMap(HardwareMap map){
        hardwareMap = map;
    }
    public static void initAll() {
        DTmotorLeft = hardwareMap.get(DcMotor.class, "DTmotorLeft");
        DTmotorRight = hardwareMap.get(DcMotor.class, "DTmotorRight");
    }
    public static void setDrivetrainPower(double   leftPower, double rightPower){
        DTmotorLeft.setPower(leftPower);
        DTmotorRight.setPower(rightPower);
    }


}
