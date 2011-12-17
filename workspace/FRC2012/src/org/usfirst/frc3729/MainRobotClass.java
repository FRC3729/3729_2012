package org.usfirst.frc3729;

/**
 * \file MainRobotClass.java
 * \brief The main class from which execution starts, as mandated by WPILib
 * 
 * 
 * 
 */

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobotClass extends IterativeRobot {
    private Input input_manager;
    private Drive drive;
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	this.input_manager = new Input();
    	this.input_manager.setMode('m');
    	this.drive = new Drive();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Drive t3h robot
    	this.drive.drive_mecanum(this.input_manager.getX(), this.input_manager.getY(), this.input_manager.getZ());
    }
    
}
