package org.firstinspires.ftc.teamcode.interfaces;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.SmartController;

public interface Subsystem {
    void initialize(HardwareMap hardwareMap, Telemetry telemetry);
    void stop();

    void execute(SmartController controller);
}
