package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class SnakeByteOpMode extends OpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;

    public DcMotor wristMotor;
    public DcMotor elbowMotor;
    public DcMotor shoulderMotor;
    public Servo claw1Servo;
    public Servo claw2Servo;

    public void init(){
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        wristMotor = hardwareMap.dcMotor.get("wristMotor");
        elbowMotor = hardwareMap.dcMotor.get("elbowMotor");
        shoulderMotor = hardwareMap.dcMotor.get("shoulderMotor");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wristMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wristMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elbowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoulderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void startMotors(double l, double r){
        motorFL.setPower(l);
        motorBL.setPower(l);
        motorFR.setPower(r);
        motorBR.setPower(r);
    }

    public void stopMotors(){
        motorFL.setPower(0);
        motorBL.setPower(0);
        motorFR.setPower(0);
        motorBR.setPower(0);
    }


}
