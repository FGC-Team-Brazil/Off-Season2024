package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware.setHardwareMap(hardwareMap);
        RobotHardware.init();
        while (opModeIsActive()) {
            loopRobot();
        }
    }

    public void loopRobot() {
        Drivetrain.getInstance().arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

}
