package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Library.Drivetrain;

@Autonomous(name = "SampleAuto", group = "4546")
public class Auto1 extends LinearOpMode {
    private Drivetrain drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(this);

        waitForStart();

        drivetrain.moveInches(10, .5);
        sleep(1000);
        drivetrain.moveTime(3000, .6);
        sleep(1000);
        drivetrain.turnPI(90,.5,.5,3);
        sleep(1000);
        drivetrain.moveGyro(.6,20,90);
    }
}
