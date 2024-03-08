package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_OPEN_POSITION;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Autonomous.PoseStorage;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;

@Config
@TeleOp(name="MainTeleOp")
public class MainTeleOp extends LinearOpMode {

    private SampleMecanumDrive drive;
    private ServoEx clawL, clawR;
    private MecanumDrive mec_drive;
    private GamepadEx driverController1, driverController2;
    private double leftTrigger, rightTrigger;
    private double driveSpeed = 0.75;

    private Motor linearLeft, linearRight;
    private MotorGroup linearLift;
    @Override
    public void runOpMode() throws InterruptedException {
        driverController1 = new GamepadEx(gamepad1);
        driverController2 = new GamepadEx(gamepad2);

        linearLeft = new Motor(hardwareMap, "vsLeft");
        linearRight = new Motor(hardwareMap, "vsRight");

        linearLift = new MotorGroup(linearLeft, linearRight);
        linearLift.resetEncoder();


        linearRight.setInverted(true);
        List<Integer> lastTrackingEncPositions = new ArrayList<>();
        List<Integer> lastTrackingEncVels = new ArrayList<>();

        StandardTrackingWheelLocalizer myLocalizer = new StandardTrackingWheelLocalizer(hardwareMap, lastTrackingEncPositions, lastTrackingEncVels);
        myLocalizer.setPoseEstimate(PoseStorage.currentPose);
        Pose2d myPose = myLocalizer.getPoseEstimate();

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

        TrajectorySequence redHang = drive.trajectorySequenceBuilder(myPose)
                .lineToLinearHeading(new Pose2d(-23.5, -59.5, Math.toRadians(0)))
                .waitSeconds(1)
                .addTemporalMarker(() -> linearLift(1000))
                .addTemporalMarker(() -> linearLift(0))
                .build();

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

            if(gamepad2.dpad_up){
                linearLift(1000);
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

    private void linearLift(int position){
        linearLift.setTargetPosition(position);
        while(linearRight.motor.getCurrentPosition() != linearRight.motor.getTargetPosition()){
            linearRight.set(1);
            linearLeft.set(1);
        }

    }
}
