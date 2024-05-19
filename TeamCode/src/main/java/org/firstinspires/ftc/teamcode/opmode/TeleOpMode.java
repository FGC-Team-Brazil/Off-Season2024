package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp (name = "Teste")
public class TeleOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware.setHardwareMap(hardwareMap);
        RobotHardware.initAll();
        while (opModeIsActive()) {
            loopRobot();
        }
    }

    public void loopRobot() {
        Drivetrain.arcadeDrive(gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

}
