package org.firstinspires.ftc.teamcode.interfaces;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Subsystem {
    void initialize(HardwareMap hardwareMap, Telemetry telemetry);
    void stop();
}
