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
    Sensors sensor;
    // This is a test comment.
    // Test Comment 2

    //hi

    public Drivetrain(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorFL = this.opMode.hardwareMap.dcMotor.get("motorFL");
        motorFR = this.opMode.hardwareMap.dcMotor.get("motorFR");
        motorBL = this.opMode.hardwareMap.dcMotor.get("motorBL");
        motorBR = this.opMode.hardwareMap.dcMotor.get("motorBR");

        countsPerInch = EncodersPerInch(560, 0.5, (100/25.4));

        time = new ElapsedTime();
        sensor = new Sensors(opMode);
    }

    public void startMotors(double left, double right){
        motorFL.setPower(left);
        motorFR.setPower(right);
        motorBL.setPower(left);
        motorBR.setPower(right);
    }

    public void stopMotors(){
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
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

    public void moveGyro(double power, double inches, double heading){
        resetEncoders();
        double constant = .6; //to reduce power of one side of drivetrain
        if (power > 0){
            while (getEncoderAvg() < inches * countsPerInch && opMode.opModeIsActive()){ //while our avg encoder value is less than desired number of encoder ticks
                if (sensor.getTrueDiff(heading) > 1){ //if our robot is off its heading to the left (needs to turn right)
                    startMotors(power, power * constant); //apply less power to right side so we turn right to maintain our heading
                }
                else if (sensor.getTrueDiff(heading) < -1){ //if our robot is off its heading to the right (needs to turn left)
                    startMotors(power * constant, power); //apply less power to left side so we turn left to maintain our heading
                }
                else{
                    startMotors(power, power);
                }
            }
        }
        else{
            while (getEncoderAvg() < inches * countsPerInch && opMode.opModeIsActive()){ //everything is swapped when we move backwards
                if (sensor.getTrueDiff(heading) > 1){
                    startMotors(power * constant, power);
                }
                else if (sensor.getTrueDiff(heading) < -1){
                    startMotors(power, power * constant);
                }
                else{
                    startMotors(power, power);
                }
            }
        }
    }

    public void turnPI(double angle, double p, double i, double timeout){
        time.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currTime = time.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = sensor.getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > 1 && time.seconds() < timeout){
            pastTime = currTime;
            currTime = time.milliseconds();
            double dT = currTime - pastTime;
            angleDiff = sensor.getTrueDiff(angle);
            P = angleDiff * kP;
            I += angleDiff * dT * kI;
            changePID = P;
            changePID += I;
            if (changePID < 0){
                startMotors(changePID - .10, -changePID + .10);
            }
            else{
                startMotors(changePID + .10, -changePID - .10);
            }
        }
        stopMotors();
    }


}
