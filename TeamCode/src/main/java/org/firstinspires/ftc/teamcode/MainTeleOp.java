package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_OPEN_POSITION;



import android.widget.Button;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Encoder;

@Config
@TeleOp(name="MainOpMode")
public class MainTeleOp extends LinearOpMode {

    private SampleMecanumDrive drive;
    private ServoEx clawL, clawR;
    private MecanumDrive mec_drive;
    private GamepadEx driverController1, driverController2;
    private double leftTrigger, rightTrigger;
    private double driveSpeed = 0.75;
    @Override
    public void runOpMode() throws InterruptedException {
        driverController1 = new GamepadEx(gamepad1);
        driverController2 = new GamepadEx(gamepad2);

        telemetry.addData("driveSpeed", driveSpeed);

        ButtonReader buttonReaderA = new ButtonReader(driverController1, GamepadKeys.Button.A);
        ButtonReader buttonReaderB = new ButtonReader(driverController1, GamepadKeys.Button.B);
        ButtonReader buttonReaderX = new ButtonReader(driverController1, GamepadKeys.Button.X);
        ButtonReader buttonReaderY = new ButtonReader(driverController1, GamepadKeys.Button.Y);

        drive = new SampleMecanumDrive(hardwareMap);

        clawL = new SimpleServo(hardwareMap, "clawL", 0, 360);
        clawR = new SimpleServo(hardwareMap, "clawR", 90, 360);
        clawR.setInverted(true);
        closeClaw();

        waitForStart();
        while(opModeIsActive()) {
            telemetry.update();

            leftTrigger = driverController1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
            rightTrigger = driverController1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);

            if (gamepad2.y) {
                openClaw();
            }

            if (gamepad2.a){
                closeClaw();
            }

            if (gamepad2.x){
                slowDrive();
            }

            //--------------------------------------------------------------

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * driveSpeed,
                            -gamepad1.left_stick_x * driveSpeed,
                            -gamepad1.right_stick_x * driveSpeed
                    )
            );

            //-------------------------------------------------------------
        }
    }

    private void openClaw(){
        clawL.setPosition(LEFT_CLAW_OPEN_POSITION);
        clawR.setPosition(RIGHT_CLAW_OPEN_POSITION);
    }

    private void closeClaw(){
        clawL.setPosition(LEFT_CLAW_CLOSE_POSITION);
        clawR.setPosition(RIGHT_CLAW_CLOSE_POSITION);
    }

    private void slowDrive(){
        driveSpeed = 0.25;
    }
}
