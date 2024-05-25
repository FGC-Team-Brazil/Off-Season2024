package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.SmartController;
import org.firstinspires.ftc.teamcode.util.StaticHeading;

import java.util.ArrayList;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Teste")
public class TeleOpMode extends OpMode {

    private ArrayList<Subsystem> subsystemsDriver;
    private ArrayList<Subsystem> subsystemsOperator;
    private SmartController driver;
    private SmartController operator;


    private StaticHeading PIDController;

    @Override
    public void init() {
        this.driver = new SmartController(gamepad1);
        this.operator = new SmartController(gamepad2);
        this.PIDController = new StaticHeading();

        this.subsystemsDriver = new ArrayList<Subsystem>();
        this.subsystemsOperator = new ArrayList<Subsystem>();

        this.subsystemsDriver.add(Drivetrain.getInstance());

        subsystemsDriver.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));
        subsystemsOperator.forEach(subsystem -> subsystem.initialize(hardwareMap, telemetry));


    }

    @Override
    public void loop() {
        subsystemsDriver.forEach(subsystem -> subsystem.execute(driver));
        subsystemsOperator.forEach(subsystem -> subsystem.execute(operator));

        //Exemple PID
        PIDController.PIDControl(1000,20,false);

    }

    @Override
    public void stop() {
        subsystemsDriver.forEach(Subsystem::stop);
        subsystemsOperator.forEach(Subsystem::stop);
    }
}
