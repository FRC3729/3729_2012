/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    private Joystick controller;
    private double xmax = 0.0, ymax = 0.0, zmax = 0.0, tmax = 0.0;
    private double x = 0.0, y = 0.0, z = 0.0, t = 0.0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit() {
        controller = new Joystick(1);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        this.x = controller.getX();
        this.y = controller.getY();
        this.z = controller.getZ();
        this.z = controller.getTwist();
        System.out.println("X: " + this.x + "Max: " + this.xmax);
        System.out.println("Y: " + this.y + "Max: " + this.ymax);
        System.out.println("Z: " + this.z + "Max: " + this.zmax);
        System.out.println("T: " + this.t + "Max: " + this.tmax);
        if (this.t > this.tmax) this.tmax = this.t;
        if (this.z > this.zmax) this.zmax = this.z;
        if (this.x > this.xmax) this.xmax = this.x;
        if (this.y > this.ymax) this.ymax = this.y;
        System.out.println("Button 1: " + this.controller.getRawButton(1));
        System.out.print(" Button 2: " + this.controller.getRawButton(2));
        System.out.println("Button 3: " + this.controller.getRawButton(3));
        System.out.print(" Button 4: " + this.controller.getRawButton(4));
        System.out.println("Button 5: " + this.controller.getRawButton(5));
        System.out.print(" Button 6: " + this.controller.getRawButton(6));
        System.out.println("Button 7: " + this.controller.getRawButton(7));
        System.out.print(" Button 8: " + this.controller.getRawButton(8));
        System.out.println("Button 9: " + this.controller.getRawButton(9));
        System.out.print(" Button 0: " + this.controller.getRawButton(10));
    }
    
}
