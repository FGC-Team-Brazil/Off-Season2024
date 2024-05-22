package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.SmartController;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOp extends OpMode {

    private SmartController driver;
    private SmartController operator;
    @Override
    public void init() {
        RobotHardware.setHardwareMap(hardwareMap);
        RobotHardware.init();
        driver = new SmartController(gamepad1);
        operator = new SmartController(gamepad2);
    }

    @Override
    public void loop() {
        Drivetrain.getInstance().arcadeDrive(-driver.getLeftStickY(), driver.getRightStickX());
    }

    @Override
    public void stop() {
        Drivetrain.getInstance().stop();
    }

}
