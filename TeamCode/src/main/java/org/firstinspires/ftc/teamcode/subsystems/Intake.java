package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;



import static com.qualcomm.robotcore.hardware.DcMotor.*;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.teamcode.constants.IntakeConstants;


public class Intake {
        private final DcMotor intakeMotorRight;
        private final DcMotor intakeMotorLeft;

        public Intake(){
            intakeMotorRight = hardwareMap.get(DcMotor.class, IntakeConstants.INTAKE_RIGTH);
            intakeMotorLeft= hardwareMap.get(DcMotor.class, IntakeConstants.INTAKE_LEFT);

            intakeMotorRight.setMode(RunMode.RUN_WITHOUT_ENCODER);
            intakeMotorLeft.setMode(RunMode.RUN_WITHOUT_ENCODER);
        }

        public void powerIntakeMotor (double output){
            intakeMotorLeft.setPower(-output);
            intakeMotorRight.setPower(-output);


            intakeMotorLeft.setTargetPosition(50);
        }





        public void stop(){
            intakeMotorRight.setPower(0);
            intakeMotorLeft.setPower(0);
        }
    }



