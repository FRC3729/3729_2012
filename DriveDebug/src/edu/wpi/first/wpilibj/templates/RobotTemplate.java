/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    private RobotDrive drive;
    private Input input;
    
    public void robotInit() {
        drive = new RobotDrive(Params.fr_port, Params.br_port, Params.fl_port, Params.bl_port);
        input = new Input();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit() {
        drive.arcadeDrive(0, 0);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //this.drive.arcadeDrive(0.5, 0.5);
        this.drive.arcadeDrive(this.input.get_x(), this.input.get_y());
        System.out.println("X: " + this.input.get_x() + "Y: " + this.input.get_y());
    }
    
}
