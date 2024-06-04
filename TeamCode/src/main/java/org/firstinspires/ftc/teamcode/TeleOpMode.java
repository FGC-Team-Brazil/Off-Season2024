package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.DepositBox;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.LinearSlide;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SmartController;

import java.util.ArrayList;

@TeleOp(name = "TeleOperado", group = "Teleoperados Oficiais")
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

        this.subsystemsOperator.add(LinearSlide.getInstance());
        this.subsystemsOperator.add(Intake.getInstance());
        this.subsystemsOperator.add(DepositBox.getInstance());

        subsystemsDriver.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
        subsystemsOperator.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
        telemetry.update();
    }

    @Override
    public void start() {
        subsystemsDriver.forEach(Subsystem::start);
        subsystemsOperator.forEach(Subsystem::start);
        telemetry.update();
    }

    @Override
    public void loop() {
        subsystemsDriver.forEach(subsystem -> subsystem.execute(driver, telemetry));
        subsystemsOperator.forEach(subsystem -> subsystem.execute(operator, telemetry));
        telemetry.update();
    }

    @Override
    public void stop() {
        subsystemsDriver.forEach(Subsystem::stop);
        subsystemsOperator.forEach(Subsystem::stop);
        telemetry.update();
    }
}
