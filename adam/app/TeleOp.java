/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import edu.wpi.first.wpilibj.Joystick;
import inputs.Driver;
import interfaces.RobotIntf;

/**
 *
 * @author adam
 */
public class TeleOp
{
    private  Joystick joy = null;
    private  Driver driver = null;
    private RobotIntf robot = null;

    public TeleOp(RobotIntf robot)
    {
        this.robot = robot;
    }

    public  void Init()
    {
        joy = new Joystick(1);
        driver = new Driver(joy);
    }

    public  void Periodic()
    {

        double z = driver.getZ();

        robot.dribblerSpeed(z);

        //System.out.print("z: " + z + "   ");


        if (driver.getLeftSw() != true /*&& drLimit.getVoltage() < 2.0*/)
        {
            robot.kickerKick();
        }


        double fwd = driver.getY();
        double slide = driver.getX();
        double rot = driver.getRot();
        robot.driveMove(fwd, slide, rot);

        //System.out.println("y: " + y + " x: " + x + " rot: " + rot);

        //System.out.println("  sw: " + this.shLimit.getVoltage() +
        //        "  dr: " + this.drLimit.getVoltage());
    }
}
