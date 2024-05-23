package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.SmartController;

import java.util.ArrayList;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOpMode extends OpMode {

    private ArrayList<Subsystem> Subsystems;
    private SmartController driver;
    private SmartController operator;
    @Override
    public void init() {
        this.driver = new SmartController(gamepad1);
        this.operator = new SmartController(gamepad2);
        this.Subsystems = new ArrayList<Subsystem>();
        this.Subsystems.add(Drivetrain.getInstance());

        Subsystems.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
    }

    @Override
    public void loop() {
        Drivetrain.getInstance().arcadeDrive(-driver.getLeftStickY(), driver.getRightStickX());
    }

    @Override
    public void stop() {
        Subsystems.forEach(Subsystem::stop);
    }
}
