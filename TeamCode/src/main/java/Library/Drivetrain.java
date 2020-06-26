package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain{
    public DcMotor motorFL;
    public DcMotor motorFR;
    public DcMotor motorBL;
    public DcMotor motorBR;
    LinearOpMode opMode;
    public double countsPerInch;
    ElapsedTime time;
    // This is a test comment.

    public Drivetrain(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorFL = this.opMode.hardwareMap.dcMotor.get("motorFL");
        motorFR = this.opMode.hardwareMap.dcMotor.get("motorFR");
        motorBL = this.opMode.hardwareMap.dcMotor.get("motorBL");
        motorBR = this.opMode.hardwareMap.dcMotor.get("motorBR");

        countsPerInch = EncodersPerInch(560, 0.5, (100/25.4));

        time = new ElapsedTime();
    }

    public void startMotors(double left, double right){
        motorFL.setPower(left);
        motorFR.setPower(right);
        motorBL.setPower(left);
        motorBR.setPower(right);
    }

    public double EncodersPerInch(double encoders, double gearReduction, double wheelDiameter){
        return ((encoders * gearReduction) /(wheelDiameter * Math.PI) );
    }

    public double getEncoderAvg(){
        int count = 4;
        if (motorFL.getCurrentPosition() == 0){
            count--;
        }
        if (motorFR.getCurrentPosition() == 0){
            count--;
        }
        if (motorBL.getCurrentPosition() == 0){
            count--;
        }
        if (motorBR.getCurrentPosition() == 0){
            count--;
        }
        if (count == 0) count++;
        return (Math.abs(motorFL.getCurrentPosition()) +
                Math.abs(motorFR.getCurrentPosition()) +
                Math.abs(motorBL.getCurrentPosition()) +
                Math.abs(motorBR.getCurrentPosition())) / count;
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

    public void moveInches(double inches, double power){
        resetEncoders();
        while (getEncoderAvg() < inches * countsPerInch){
            startMotors(power, power);
        }
    }

    public void moveTime(int millis, double power){
        time.reset();
        while (time.milliseconds() < millis){
            startMotors(power, power);
        }
    }
}
