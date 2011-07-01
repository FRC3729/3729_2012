/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import interfaces.RobotIntf;

/**
 *
 * @author adam
 */
public class Autonomous
{

    private final double AUTO_SPEED = 0.4;
    private final double K_PID = 0.04;
    private final double K_DEV = 0.25;
    private int state = 0;
    private Timer timer = null;
    private RobotIntf robot = null;
    private PIDController fwdController = null;
    private PIDController slideController = null;
    private Gyro gyro = null;

    public Autonomous(RobotIntf robot)
    {
        this.robot = robot;

        timer = new Timer();

        gyro = robot.getGyro();
    }

    public void Init()
    {
        state = 0;

        // This PID Controller handles driving straight
        fwdController = new PIDController(K_PID, 0.0, K_DEV, gyro, new PIDOutput()
        {

            public void pidWrite(double output)
            {
                robot.driveMove(AUTO_SPEED, 0.0, output);
            }
        });     //, .005);
        
        // This PID Controller handles sliding to the left
        slideController = new PIDController(K_PID, 0.0, K_DEV, gyro, new PIDOutput()
        {

            public void pidWrite(double output)
            {
                robot.driveMove(0.0, AUTO_SPEED, output);
            }
        });     //, .005);

        fwdController.disable();
        slideController.disable();
    }

    public void Periodic()
    {
        switch (state)
        {
            case 0:
                System.out.println("auto 0");
                timer.reset();
                timer.start();
                state++;
                break;

            case 1:
                System.out.println("auto 1");
                if (timer.get() > 1)
                {
                    state++;
                }
                break;

            case 2:
                System.out.println("auto 2");
                timer.reset();
                timer.start();

                gyro.reset();
                fwdController.setSetpoint(0.0);
                fwdController.enable();

                //robot.dribbler.setSpeed(Dribbler.LOW);
                robot.dribblerSpeed(0);

                robot.kickerArm();

                state++;
                break;

            case 3:
                double time = timer.get();
                System.out.println("auto 3: " + time);

                // kick the ball if we find it
                if (robot.dribblerLimitOn())
                {
                    robot.kickerKick();
                }

                if (time > 4)
                {
                    state++;
                }
                break;

            case 4:
                System.out.println("auto 4");

                // stop forward movement
                //robot.dribbler.setSpeed(Dribbler.OFF);
                robot.dribblerSpeed(-1.0);
                fwdController.disable();
                fwdController = null;
                //robot.dt.stop();
                robot.driveStop();

                // set up for moving right 2 seconds
                timer.reset();
                timer.start();
                slideController.enable();
                slideController.setSetpoint(0.0);
                state++;
                break;

            case 5:         // Move right to get out of the way
                time = timer.get();
                System.out.println("auto 5: " + time);
                if (time > 2)
                {
                    state++;
                }
                break;

            case 6:
                System.out.println("auto 6");
                slideController.disable();
                slideController = null;
                robot.driveStop();
                state++;
                break;

            case 7:
                System.out.println("auto 7");
                break;
        }
    }
}
