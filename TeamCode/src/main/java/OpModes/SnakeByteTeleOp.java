package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode {

    boolean tank = false;

    public void loop() {
        double left = 0;
        double right = 0;
        double max;

        //speed constant (half speed when left bumper pressed)
        double sC;
        if (gamepad1.left_bumper) {
            sC = .5;
        }
        else {
            sC = 1;
        }

        if (Math.abs(gamepad2.left_stick_y) > .1 || Math.abs(gamepad2.right_stick_y) > .1) {
            shoulderMotor.setPower(gamepad2.left_stick_y);
            elbowMotor.setPower(gamepad2.right_stick_y);
        }

        //tank drive controls (Y value of left stick is for left side of drivetrain & vice versa)
        if (tank) {
            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_y)) > .1) {
                startMotors(gamepad1.left_stick_y * sC, gamepad1.right_stick_y * sC);
            } else {
                stopMotors();
            }
        } else { //arcade drive controls
            if (Math.abs(gamepad1.left_stick_y) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
                left = (gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y)) -
                        (gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x));
                right = (gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y)) +
                        (gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x));
                max = Math.max(Math.abs(left), Math.abs(right));
                if (max > 1.0) {
                    left /= max;
                    right /= max;
                }

                startMotors(left * sC, right * sC);
            } else {
                stopMotors();
            }
        }
    }
}
