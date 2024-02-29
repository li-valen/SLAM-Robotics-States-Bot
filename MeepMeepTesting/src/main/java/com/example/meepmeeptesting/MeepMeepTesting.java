package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // Option: Set theme. Default = ColorSchemeRedDark()
                .setColorScheme(new ColorSchemeBlueDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-42, 30, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, 10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, 10, Math.toRadians(0)))
                                .lineToConstantHeading(new Vector2d(55, 42))
                                .build()
                );

        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-34, 19, Math.toRadians(90)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, 10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, 10, Math.toRadians(0)))
                                .lineToConstantHeading(new Vector2d(55, 35))
                                .build()
                );

        RoadRunnerBotEntity myThirdBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-34, 32, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-27, 32, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, 10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, 10, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, 29))
                                .build()
                );

        RoadRunnerBotEntity myFourthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-42, -30, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, -10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, -10, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -29))
                                .build()
                );

        RoadRunnerBotEntity myFifthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-34, -20, Math.toRadians(270)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, -10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, -10, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -35))
                                .build()
                );

        RoadRunnerBotEntity mySixthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-34, -32, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-27, -32, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(-34, -10, Math.toRadians(0)))
                                .waitSeconds(5)
                                .lineToLinearHeading(new Pose2d(55, -10, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -42))
                                .build()
                );

        RoadRunnerBotEntity mySeventhBot = new DefaultBotBuilder(meepMeep) //blueClose
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // Option: Set theme. Default = ColorSchemeBlueLight()
                .setColorScheme(new ColorSchemeBlueLight())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(27, 30, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55,42, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, 60))
                                .build()
                );

        RoadRunnerBotEntity myEighthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(12, 32, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(3, 32, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, 29, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, 60))
                                .build()
                );

        RoadRunnerBotEntity myNinthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(12, 28, Math.toRadians(270)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, 35, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, 60))
                                .build()
                );

        RoadRunnerBotEntity myTenthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(12, -30, Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(3, -32, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, -29, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -62))
                                .build()
                );

        RoadRunnerBotEntity myEleventhBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(12, -27, Math.toRadians(90)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, -35, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -62))
                                .build()
                );

        RoadRunnerBotEntity myTwelfthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(27, -30, Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, -42, Math.toRadians(0)))
                                .waitSeconds(1)
                                .lineToConstantHeading(new Vector2d(55, -62))
                                .build()
                );

        // Set field image
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                // Background opacity from 0-1
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(mySecondBot)
                .addEntity(myThirdBot)
                .addEntity(myFourthBot)
                .addEntity(mySixthBot)
                .addEntity(myFifthBot)
                .addEntity(mySeventhBot)
                .addEntity(myEighthBot)
                .addEntity(myNinthBot)
                .addEntity(myTenthBot)
                .addEntity(myEleventhBot)
                .addEntity(myTwelfthBot)
                .start();
    }
}