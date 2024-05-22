package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOp extends OpMode {

    @Override
    public void init() {
        RobotHardware.setHardwareMap(hardwareMap);
        RobotHardware.init();
    }

    @Override
    public void loop() {
        Drivetrain.getInstance().arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

    @Override
    public void stop() {
        Drivetrain.getInstance().stop();
    }

}
