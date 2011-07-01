/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package app;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import interfaces.RobotIntf;
import systems.RamLight;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot
{
    private RobotIntf robot = null;
    private Autonomous auto = null;
    private TeleOp tele = null;
    private RamLight light = null;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        //System.out.println("robotInit");

        robot = new CompetitionRobot();
        //robot = new PracticeRobot();

        robot.init();

        getWatchdog().setExpiration(.75);

        //light = new RamLight(robot);

        // Wait for gyro to stabilize
        Timer.delay(10.0);
    }

    public void autonomousInit()
    {
        Watchdog.getInstance().feed();

        //System.out.println("autonomousInit");

        auto = new Autonomous(robot);

        auto.Init();

        // start the thread that lights the RAM light if we have a ball
        //light.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        Watchdog.getInstance().feed();

        //System.out.println("autonomousPeriodic");

        // Update robot systems
        robot.update();

        auto.Periodic();
    }

    public void teleopInit()
    {
        Watchdog.getInstance().feed();

        //System.out.println("teleopInit");

        tele = new TeleOp(robot);

        tele.Init();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        Watchdog.getInstance().feed();

        //System.out.println("teleopPeriodic");

        // Update robot systems
        robot.update();

        tele.Periodic();
    }
}
