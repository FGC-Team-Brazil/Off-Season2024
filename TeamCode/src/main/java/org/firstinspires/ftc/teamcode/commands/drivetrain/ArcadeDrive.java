package org.firstinspires.ftc.teamcode.commands.drivetrain;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class ArcadeDrive {
    public ArcadeDrive(Drivetrain drivetrain, double forward, double rotation){
        if(forward != 0 && rotation != 0){

        }else {
            drivetrain.stop();
        }

    }

}
