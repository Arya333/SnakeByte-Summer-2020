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

    public int getEncoderWrist()
    {
        return Math.abs(wristMotor.getCurrentPosition());
    }

    public int getEncoderElbow()
    {
        return Math.abs(elbowMotor.getCurrentPosition());
    }

    public int getEncoderShoulder()
    {
        return Math.abs(shoulderMotor.getCurrentPosition());
    }

    void moveWrist(double power, double encoder)
    {
        resetEncoders();
        while (getEncoderWrist() < encoder) {
            wristMotor.setPower(power);
        }
        wristMotor.setPower(0);
    }

    void moveElbow(double power, double encoder)
    {
        resetEncoders();
        while (getEncoderElbow() < encoder) {
            elbowMotor.setPower(power);
        }
        elbowMotor.setPower(0);
    }

    void moveShoulder(double power, double encoder)
    {
        resetEncoders();
        while (getEncoderShoulder() < encoder) {
            shoulderMotor.setPower(power);
        }
        shoulderMotor.setPower(0);
    }
}
