package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.RobotConstants.LIFT_SWIVEL_UP_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_CLOSE_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.LEFT_CLAW_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.RobotConstants.RIGHT_CLAW_OPEN_POSITION;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;
import java.util.List;

@Config

@Autonomous(name = "RedClose")
public class RedClose extends LinearOpMode {

    double locationOfProp = 0;

    double cX = 0;
    double cY = 0;
    double width = 0;

    private OpenCvCamera controlHubCam;  // Use OpenCvCamera class from FTC SDK
    private static final int CAMERA_WIDTH = 1280; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 720; // height of wanted camera resolution

    // Calculate the distance using the formula
    public static final double objectWidthInRealWorldUnits = 3.75;  // Replace with the actual width of the object in real-world units
    public static final double focalLength = 728;  // Replace with the focal length of the camera in pixels
    private static final double fx = 578.272;
    private static final double fy = 578.272;
    private static final double cx = 402.145;
    private static final double cy = 221.506;
    public VisionPortal visionPortal;
    public RedClose.RedDetectionProcessor redDetectionProcessor;
    public AprilTagProcessor aprilTagProcessor;
    public static int lowH = 100;

    public static int lowS = 100;

    public static int lowV = 100;

    public static int highH = 180;

    public static int highS = 255;

    public static int highV = 255;
    public final int RED_ALLIANCE_LEFT = 4;
    public final int RED_ALLIANCE_CENTER = 5;
    public final int RED_ALLIANCE_RIGHT = 6;
    public SampleMecanumDrive drive;
    public SimpleServo swivel, clawR;
    @Override
    public void runOpMode() {
        drive = new SampleMecanumDrive(hardwareMap);

        clawR = new SimpleServo(hardwareMap, "clawR", 0, 360);
        swivel = new SimpleServo(hardwareMap, "swivel", 0, 360);

        clawR.setInverted(true);
        closeClaws();

        initVision();
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        Pose2d startPose = new Pose2d(12, -60, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        //TRAJECTORIES FOR IF PROP LOCATION IS 1

        //TRAJECTORIES FOR IF PROP LOCATION IS 2

        //TRAJECTORIES FOR IF PROP LOCATION IS 3
        //init loop
        while (!isStopRequested() && !isStarted()) {
            getLocationOfProp();
            telemetry.addData("Location of Prop", locationOfProp);
            telemetry.update();
            sleep(1000);
        }

        //start button just pressed
        if (locationOfProp == 1) {
            TrajectorySequence leftTraj1 = drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                    .lineToLinearHeading(new Pose2d(12, -30, Math.toRadians(180)))
                    .lineToLinearHeading(new Pose2d(3, -32, Math.toRadians(180)))
                    .addTemporalMarker(()-> openRightClaw())
                    .waitSeconds(1)
                    .lineToLinearHeading(new Pose2d(55, -29, Math.toRadians(0)))
                    .addTemporalMarker(()-> openSwivel())
                    .waitSeconds(1)
                    .lineToConstantHeading(new Vector2d(55, -62))
                    .build();
            drive.followTrajectorySequence(leftTraj1);
        }

        if(locationOfProp == 2) {
            TrajectorySequence midTraj1 = drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                    .lineToLinearHeading(new Pose2d(12, -27, Math.toRadians(90)))
                    .addTemporalMarker(()-> openRightClaw())
                    .waitSeconds(1)
                    .lineToLinearHeading(new Pose2d(55, -35, Math.toRadians(0)))
                    .addTemporalMarker(()-> openSwivel())
                    .waitSeconds(1)
                    .lineToConstantHeading(new Vector2d(55, -62))
                    .build();
            drive.followTrajectorySequence(midTraj1);
        }

        if(locationOfProp == 3) {
            TrajectorySequence rightTraj1 = drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
                    .lineToLinearHeading(new Pose2d(27, -30, Math.toRadians(180)))
                    .addTemporalMarker(()-> openRightClaw())
                    .waitSeconds(1)
                    .lineToLinearHeading(new Pose2d(55, -42, Math.toRadians(0)))
                    .addTemporalMarker(()-> openSwivel())
                    .waitSeconds(1)
                    .lineToConstantHeading(new Vector2d(55, -62))
                    .build();
            drive.followTrajectorySequence(rightTraj1);
        }

        // The OpenCV pipeline automatically processes frames and handles detection
        // Release resources
        PoseStorage.currentPose = drive.getPoseEstimate();
        visionPortal.close();
    }

    private void openSwivel() {
        swivel.setPosition(LIFT_SWIVEL_UP_POSITION);
    }

    private void openRightClaw() {
        clawR.setPosition(RIGHT_CLAW_OPEN_POSITION);
    }

    private void closeClaws() {
        clawR.setPosition(RIGHT_CLAW_CLOSE_POSITION);
    }

    private void alignWithAprilTag(int id) {
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        int addedXOffset = 0;

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (id == RED_ALLIANCE_LEFT) {
                    addedXOffset = 2;
                }
                else if (id == RED_ALLIANCE_RIGHT) {
                    addedXOffset = -2;
                }

                if (detection.id == id) {
                    double xOffset = detection.ftcPose.x;
                    double yOffset = detection.ftcPose.y;
                    TrajectorySequence alignWithAprilTag = drive.trajectorySequenceBuilder(new Pose2d(48, 32, Math.toRadians(180)))
                            .back(yOffset)
                            .strafeRight(-xOffset + addedXOffset)
                            .turn(Math.toRadians(180))
                            .build();
                    drive.followTrajectorySequence(alignWithAprilTag);
                }
            }
        }
    }

    public void initVision() {
        redDetectionProcessor = new RedClose.RedDetectionProcessor();

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setDrawTagOutline(true)
                .setLensIntrinsics(fx, fy, cx, cy)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new android.util.Size(800, 448))
                .enableLiveView(true)
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(false)
                .addProcessors(aprilTagProcessor, redDetectionProcessor)
                .build();
    }
    class RedDetectionProcessor implements VisionProcessor, CameraStreamSource {
        @Override
        public Mat processFrame(Mat input, long captureTimeNanos) {
            // Preprocess the frame to detect red regions
            Mat redMask = preprocessFrame(input);

            // Find contours of the detected red regions
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(redMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            // Find the largest red contour (blob)
            MatOfPoint largestContour = findLargestContour(contours);

            if (largestContour != null) {
                // Draw a red outline around the largest detected object
                Imgproc.drawContours(input, contours, contours.indexOf(largestContour), new Scalar(255, 0, 0), 2);
                // Calculate the width of the bounding box
                width = calculateWidth(largestContour);

                // Display the width next to the label
                String widthLabel = "Width: " + (int) width + " pixels";
                Imgproc.putText(input, widthLabel, new Point(cX + 10, cY + 20), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                //Display the Distance
                String distanceLabel = "Distance: " + String.format("%.2f", getDistance(width)) + " inches";
                Imgproc.putText(input, distanceLabel, new Point(cX + 10, cY + 60), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                // Calculate the centroid of the largest contour
                Moments moments = Imgproc.moments(largestContour);
                //gets center of object
                cX = moments.get_m10() / moments.get_m00();
                cY = moments.get_m01() / moments.get_m00();

                // Draw a dot at the centroid
                String label = "(" + (int) cX + ", " + (int) cY + ")";
                Imgproc.putText(input, label, new Point(cX + 10, cY), Imgproc.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(0, 255, 0), 2);
                Imgproc.circle(input, new Point(cX, cY), 5, new Scalar(0, 255, 0), -1);

            }

            return input;
        }

        private Mat preprocessFrame(Mat frame) {
            Mat hsvFrame = new Mat();
            Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

            Scalar lowerRed = new Scalar(lowH, lowS, lowV);
            Scalar upperRed = new Scalar(highH, highS, highV);


            Mat redMask = new Mat();
            Core.inRange(hsvFrame, lowerRed, upperRed, redMask);

            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
            Imgproc.morphologyEx(redMask, redMask, Imgproc.MORPH_OPEN, kernel);
            Imgproc.morphologyEx(redMask, redMask, Imgproc.MORPH_CLOSE, kernel);

            return redMask;
        }

        private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
            double maxArea = 0;
            MatOfPoint largestContour = null;

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    largestContour = contour;
                }
            }

            return largestContour;
        }
        private double calculateWidth(MatOfPoint contour) {
            Rect boundingRect = Imgproc.boundingRect(contour);
            return boundingRect.width;
        }

        @Override
        public void init(int i, int i1, CameraCalibration cameraCalibration) {

        }

        @Override
        public void onDrawFrame(Canvas canvas, int i, int i1, float v, float v1, Object o) {

        }

        @Override
        public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {

        }
    }
    private static double getDistance(double width){
        double distance = (objectWidthInRealWorldUnits * focalLength) / width;
        return distance;
    }

    private void getLocationOfProp() {
        if (cX<200) {
            locationOfProp = 1;
        }
        if (cX>=200 && cX<=600) {
            locationOfProp = 2;
        }
        if (cX>600) {
            locationOfProp=3;
        }
    }


}

