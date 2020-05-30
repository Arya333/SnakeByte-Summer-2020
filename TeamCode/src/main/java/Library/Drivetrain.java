package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Drivetrain {
    public DcMotor motorFL;
    public DcMotor motorFR;
    public DcMotor motorBL;
    public DcMotor motorBR;
    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorFL = this.opMode.hardwareMap.dcMotor.get("motorFL");
        motorFR = this.opMode.hardwareMap.dcMotor.get("motorFR");
        motorBL = this.opMode.hardwareMap.dcMotor.get("motorBL");
        motorBR = this.opMode.hardwareMap.dcMotor.get("motorBR");
    }

    public void startMotors(double left, double right){
        motorFL.setPower(left);
        motorFR.setPower(right);
        motorBL.setPower(left);
        motorBR.setPower(right);
    }

    public void resetEncoders(){
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }
}
