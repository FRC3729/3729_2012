package edu.first3729.frc2012;

/**
 * \file MainRobotClass.java
 * \brief The main class from which execution starts, as mandated by WPILib
 * 
 */

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobotClass extends IterativeRobot
{
    private Input input_manager;
    private DriverStationLCD screen;
    private Drive drive;
    private Teleoperated teleop;
    private int loop;
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
    	this.input_manager = new Input();
    	this.input_manager.setMode(Params.default_drive_mode);
    	this.drive = new Drive();
        this.teleop = new Teleoperated(input_manager, drive);
        loop = 0;
    }
    
    
    public void disabledInit()
    {
        System.out.println("If you can see this, robot code has deployed successfully.");
        System.out.println("Please click the red stop button to the left.");
    }
    
    public void disabledPeriodic()
    {
        // Nothing
    }
    
    public void teleopInit()
    {
        teleop.init();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        System.out.println("Loop " + loop);
        ++loop;
        teleop.test_buttons();
    }
    
}
