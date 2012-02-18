/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

/**
 * \file Robot.java \brief The main class from which execution starts, as
 * mandated by WPILib
 *
 */
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    private Input input_manager;
    private DriverStationLCD screen;
    private Drive drive;
    private Teleoperated teleop;
    private Manipulator manip;
    private DigitalInput intake_limit;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Print banner
        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|");
 
        // For the lulz
        System.out.println("This robot complies with Asimov's Laws of Robotics:");
        System.out.println("\t~> A robot may not injure a human being or, through inaction, allow a human being to come to harm.");
        System.out.println("\t~> A robot must obey the orders given to it by human beings, except where such orders would conflict with the First Law.");
        System.out.println("\t~> A robot must protect its own existence as long as such protection does not conflict with the First or Second Laws.");
        
        // Initialize stuff
        System.out.print("Initializing robot...");
        this.input_manager = new Input();
        this.input_manager.set_mode(Params.default_drive_mode);
        this.drive = new Drive();
        this.drive.lock_motors();
        this.manip = new Manipulator();
        this.manip.init();
        this.teleop = new Teleoperated(input_manager, drive, manip);
        
        // Set up Watchdog
        this.getWatchdog().setExpiration(Params.default_watchdog_time);
        
        System.out.println(" done!");
    }

    public void disabledInit() {
        System.out.println("Going disabled.");
    }

    public void disabledPeriodic() {
        // Nothing
    }

    public void teleopInit() {
        System.out.println("Going teleoperated.");
        teleop.init();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        this.getWatchdog().feed();
        teleop.run();
    }

    public void teleopContinuous() {
    }
}
