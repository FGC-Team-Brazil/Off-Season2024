package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.drivetrain.ArcadeDrive;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOp extends LinearOpMode {

    private Drivetrain drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware.setHardwareMap(hardwareMap);
        RobotHardware.initAll();
        this.drivetrain = new Drivetrain();
        while (opModeIsActive()) {
            loopRobot();
        }
    }

    public void loopRobot() {
        new ArcadeDrive(drivetrain, -gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

}
