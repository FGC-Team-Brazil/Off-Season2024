package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.constants.LinearSlideConstants;
import org.firstinspires.ftc.teamcode.util.ButtonListener;
import org.firstinspires.ftc.teamcode.util.SmartController;

import static org.firstinspires.ftc.teamcode.constants.DepositBoxConstants.*;
import static org.firstinspires.ftc.teamcode.constants.GlobalConstants.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.interfaces.Subsystem;

import org.firstinspires.ftc.teamcode.util.StaticHeading;

public class DepositBox implements Subsystem {
    double currentPIDGoal = 0;
    double endHeightGoal = 0;
    DcMotor DepositMotor;
    StaticHeading DepositPID;

    private static DepositBox instance;

    private DepositBox() {
    }

    @Override
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry) {
        DepositMotor = hardwareMap.get(DcMotor.class, DEPOSIT_MOTOR);

        DepositPID = new StaticHeading(kP, kI, kD, kF);

        DepositMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DepositMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        DepositMotor.setPower(0);
    }

    @Override
    public void execute(SmartController operator) {
        ButtonListener.whileTrue(operator.isButtonDPadUp())
                .and(LinearSlide.getInstance().currentPIDGoal >= LinearSlideConstants.DEPOSIT_HEIGHT_MID - 0.1)
                .run(() -> endHeightGoal = BOX_DEPOSIT_ANGLE);

        ButtonListener.whileTrue(operator.isButtonDPadDown())
                .run(() -> endHeightGoal = 0);

        advanceGoal(endHeightGoal);
        DepositPID.calculate(angleDesiredToTicks(currentPIDGoal), DepositMotor.getCurrentPosition());
    }

    private void advanceGoal(double setpoint) {
        if (currentPIDGoal + GOAL_STEP > setpoint && currentPIDGoal - GOAL_STEP < setpoint) {
            currentPIDGoal = setpoint;
        } else {
            if (currentPIDGoal > setpoint) {
                currentPIDGoal -= GOAL_STEP;
            } else if (currentPIDGoal < setpoint) {
                currentPIDGoal += GOAL_STEP;
            }
        }
    }

    private double angleDesiredToTicks(double angle) {
        return Math.round(angle / 360 * REDUCTION * CORE_HEX_TICKS_PER_REVOLUTION);
    }

    public static DepositBox getInstance() {
        if (instance == null) {
            instance = new DepositBox();
        }
        return instance;
    }

}
