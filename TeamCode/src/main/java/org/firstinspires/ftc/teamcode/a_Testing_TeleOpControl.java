package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "a_Testing_TeleOpControl")
public class a_Testing_TeleOpControl extends OpMode {

    //Declare Motors
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    private DcMotor intakeMotorFront = null;
    private DcMotor intakeMotorRear = null;

    private DcMotor slideMotor = null;

    //Declare Servos
    private CRServo boxFlipServo = null;

    //Declare misc variables
    private ElapsedTime TeleOP_runtime = new ElapsedTime();
    private double modifier_driveSpeed;

    //Run the code after 'Driver' hits INIT
    @Override
    public void init() {
        telemetry.addData("Initialising", "Started");
        telemetry.update();

        //Initialise hardware variables from configuration.
        //Phone configuration = "Testing_v2"
        //This configuration can be edited without affecting release.

        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        intakeMotorFront = hardwareMap.get(DcMotor.class, "intakeMotorFront");
        intakeMotorRear = hardwareMap.get(DcMotor.class, "intakeMotorRear");

        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        boxFlipServo = hardwareMap.get(CRServo.class, "boxFlipServo");

        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        slideMotor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Initialising", "Complete");
        telemetry.update();

    }

    //Run the loop after 'Driver' hits INIT until PLAY.
    @Override
    public void init_loop() {

    }

    //Run ONCE driver hits PLAY
    @Override
    public void start() {
        TeleOP_runtime.reset();
        modifier_driveSpeed = 0.5;
    }

    private boolean dpadPressedY = false;
    private boolean dpadPressedX = false;

    private double modifier_intakespeed = 0.5;
    //Run the loop after 'Driver hits PLAY before STOP
    @Override
    public void loop() {
        // Change drive speed by changing the modifier
        // TODO Limit modifier_drive to 1


        if (!dpadPressedY) {
            if ((!gamepad1.dpad_down && gamepad1.dpad_up) && (modifier_driveSpeed < 0.9)) {
                modifier_driveSpeed = modifier_driveSpeed + 0.1;
            } else if ((gamepad1.dpad_down && !gamepad1.dpad_up) && (modifier_driveSpeed > 0.1)) {
                modifier_driveSpeed = modifier_driveSpeed - 0.1;
            }
        }
        if (!dpadPressedX) {
            if ((!gamepad1.dpad_left && gamepad1.dpad_right) && modifier_intakespeed < 0.9) {
                modifier_intakespeed = modifier_intakespeed + 0.1;
            } else if ((!gamepad1.dpad_right && gamepad1.dpad_left) && modifier_intakespeed > 0.1) {
                modifier_intakespeed = modifier_intakespeed - 0.1;
            }
        }

        if (gamepad1.a) {
            intakeMotorFront.setDirection(DcMotorSimple.Direction.FORWARD);
            intakeMotorRear.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        if (gamepad1.y) {
            intakeMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeMotorRear.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        dpadPressedY = (gamepad1.dpad_up || gamepad1.dpad_down);
        dpadPressedX = (gamepad1.dpad_left || gamepad1.dpad_right);

        double leftInput = gamepad1.left_trigger + (gamepad1.left_bumper ? -1 : 0);
        double rightInput = gamepad1.right_trigger + (gamepad1.right_bumper ? -1 : 0);
        double slidePower = gamepad2.left_trigger + (gamepad2.left_bumper ? -1 : 0);
        double boxFlipPower = gamepad2.right_trigger + (gamepad2.right_bumper ? -1 : 0);

        double intakeInput = gamepad1.x ? 1 : 0;
        double intakePower = intakeInput * modifier_intakespeed;

        // TODO Make leftPower + rightPower = 1 for max values of leftPower and rightPower
        double leftPower = leftInput * modifier_driveSpeed;
        double rightPower = rightInput * modifier_driveSpeed;


        // Send calculated power to wheels
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // Set slide power
        slideMotor.setPower(slidePower);
        // Set boxFlip power
        boxFlipServo.setPower(boxFlipPower);
        // TODO Sync the hex motors?
        // Set intake power (hex motors)
        intakeMotorFront.setPower(intakePower);
        intakeMotorRear.setPower(intakePower);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + TeleOP_runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Slide, boxFlip", "Power (%.2f) Power (%.2f)", slidePower, boxFlipPower);
        telemetry.addData("Intake", "Power (%.2f)", intakePower);
        telemetry.update();
    }

    //Run the code once 'Driver' hits STOP.
    @Override
    public void stop() {

    }

}
