package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;


@TeleOp(name = "Testing_TeleOpControl")
public class Testing_TeleOpControl extends OpMode implements Gamepad.GamepadCallback {

    //Declare Motors
    private DcMotor leftMotor = null;
    private DcMotor rightMotor = null;

    private DcMotor intakeMotorFront = null;
    private DcMotor intakeMotorRear = null;

    //private DcMotor slideMotor = null;

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
        try {
            gamepad1.copy(new Gamepad(this));
            gamepad2.copy(new Gamepad(this));
        } catch (RobotCoreException e) {
            RobotLog.e("Gamepads could not be re-created");
            throw new IllegalStateException("Gamepads could not be re-created");
        }

        //Initialise hardware variables from configuration.
        //Phone configuration = "Testing_v2"
        //This configuration can be edited without affecting release.

        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        intakeMotorFront = hardwareMap.get(DcMotor.class, "intakeMotorFront");
        intakeMotorRear = hardwareMap.get(DcMotor.class, "intakeMotorRear");

        //slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        boxFlipServo = hardwareMap.get(CRServo.class, "boxFlip");

        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        //slideMotor.setDirection(DcMotor.Direction.FORWARD);

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
        modifier_driveSpeed = 0.2;

    }

    //Run the loop after 'Driver hits PLAY before STOP
    @Override
    public void loop() {
        // Change drive speed by changing the modifier
        // TODO Limit modifier_drive to 1
        if (!gamepad1.dpad_down && gamepad1.dpad_up) {
            modifier_driveSpeed = modifier_driveSpeed + 0.1;
        } else if (gamepad1.dpad_down && !gamepad1.dpad_up) {
            modifier_driveSpeed = modifier_driveSpeed - 0.1;
        }

        double leftInput = gamepad1.left_trigger + (gamepad1.left_bumper ? -1 : 0);
        double rightInput = gamepad1.right_trigger + (gamepad1.right_bumper ? -1 : 0);
        double slideInput = gamepad2.left_trigger + (gamepad2.left_bumper ? -1 : 0);

        double intakePower = gamepad1.x ? 1 : 0;


        // TODO Make leftPower + rightPower = 1 for max values of leftPower and rightPower
        double leftPower = leftInput * 0.5 * modifier_driveSpeed;
        double rightPower = rightInput * 0.5 * modifier_driveSpeed;
        double slidePower = slideInput * 1;

        // Send calculated power to wheels
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // Set slide power
        //slideMotor.setPower(slidePower);

        // TODO Sync the hex motors?
        // Set intake power (hex motors)
        intakeMotorFront.setPower(intakePower);
        intakeMotorRear.setPower(intakePower);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + TeleOP_runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        //telemetry.addData("Slide", "Power (%.2f)", slidePower);
        telemetry.update();
    }

    //Run the code once 'Driver' hits STOP.
    @Override
    public void stop() {

    }

    @Override
    public void gamepadChanged(Gamepad gamepad) {
        telemetry.addData("Blackmagic", "it worked");
        telemetry.update();
        throw new NullPointerException();

    }

}
