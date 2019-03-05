package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp (name = "Testing_TeleOpControl")
public class Testing_TeleOpControl extends OpMode
{

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

    //Run the code after 'Driver' hits INIT
    @Override
    public void init()
    {
        telemetry.addData("Init:", "Started");
        telemetry.update();

        //Initialise hardware variables from configuration.
        //Phone configuration = "Testing"
        //This configuration can be edited without affecting release.

        leftMotor  = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        intakeMotorFront = hardwareMap.get(DcMotor.class,"intakeMotorFront");
        intakeMotorRear = hardwareMap.get(DcMotor.class, "intakeMotorRear");

        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        boxFlipServo = hardwareMap.get(CRServo.class, "boxFlip");

        telemetry.addData("Init:", "Complete");
        telemetry.update();
    }

    //Run the loop after 'Driver' hits INIT until PLAY.
    @Override
    public void init_loop()
    {

    }
    //Run ONCE driver hits PLAY
    @Override
    public void start()
    {
        TeleOP_runtime.reset();
    }

    //Run the loop after 'Driver hits PLAY before STOP
    @Override
    public void loop()
    {

    }
    //Run the code once 'Driver' hits STOP.
    @Override
    public void stop()
    {

    }


}
