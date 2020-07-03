package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Arm
{
    public DcMotor wristMotor;
    public DcMotor elbowMotor;
    public DcMotor shoulderMotor;
    public Servo claw1Servo;
    public Servo claw2Servo;
    LinearOpMode opMode;
    ElapsedTime time;
    Sensors sensor;

    public Arm (LinearOpMode opMode) throws InterruptedException
    {
        this.opMode = opMode;
        time = new ElapsedTime();
        sensor = new Sensors(opMode);
        wristMotor = this.opMode.hardwareMap.dcMotor.get("wristMotor");
        elbowMotor = this.opMode.hardwareMap.dcMotor.get("elbowMotor");
        shoulderMotor = this.opMode.hardwareMap.dcMotor.get("shoulderMotor");
        claw1Servo = this.opMode.hardwareMap.servo.get("claw1Servo");
        claw2Servo = this.opMode.hardwareMap.servo.get("claw2Servo");
    }

    public void resetEncoders ()
    {
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        shoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        wristMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        elbowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        shoulderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }
}
