package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.LinearSlide;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import org.firstinspires.ftc.teamcode.util.SmartController;

import java.util.ArrayList;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOpMode extends OpMode {

    private ArrayList<Subsystem> subsystemsDriver;
    private ArrayList<Subsystem> subsystemsOperator;
    private SmartController driver;
    private SmartController operator;
    @Override
    public void init() {
        this.driver = new SmartController(gamepad1);
        this.operator = new SmartController(gamepad2);

        this.subsystemsDriver = new ArrayList<Subsystem>();
        this.subsystemsOperator = new ArrayList<Subsystem>();

        this.subsystemsDriver.add(Drivetrain.getInstance());
        this.subsystemsDriver.add(LinearSlide.getInstance());

        this.subsystemsOperator.add(Intake.getInstance());

        subsystemsDriver.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
        subsystemsOperator.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
    }

    @Override
    public void loop() {
        subsystemsDriver.forEach(subsystem -> subsystem.execute(driver));
        subsystemsOperator.forEach(subsystem -> subsystem.execute(operator));
    }

    @Override
    public void stop() {
        subsystemsDriver.forEach(Subsystem::stop);
        subsystemsOperator.forEach(Subsystem::stop);
    }
}
