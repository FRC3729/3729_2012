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
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive;

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
    private RobotDrive drive;
    private Teleoperated teleop;
    private Autonomous auto;
    private Manipulator manip;
    private AxisCamera camera;
    
    private boolean auto_complete;
    
    /**
     * This function is run when the robot is first started up and contains 
     */
    public void robotInit() {
        // Initialization timer
        Timer init_timer = new Timer();
        
        // Print banner
        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|\n");
 
        // For the lulz
        System.out.println("This robot complies with Asimov's Laws of Robotics:");
        System.out.println("\t~> 1. A robot may not injure a human being or,\n\t      through inaction, allow a human being to come to harm.");
        System.out.println("\t~> 2. A robot must obey the orders given to it by human beings,\n\t      except where such orders would conflict with the First Law.");
        System.out.println("\t~> 3. A robot must protect its own existence as long as\n\t      such protection does not conflict with the First or Second Laws.");
        
        // Initialize stuff
        System.out.println("=== INITIALIZING ROBOT ===");
        
        // Start timer
        init_timer.start();
        this.input_manager = new Input();
        this.drive = new RobotDrive(Params.fl_port, Params.bl_port, Params.fr_port, Params.br_port);
        this.drive.tankDrive(0, 0);
        this.manip = new ManipulatorPractice();
        this.manip.init();
        this.teleop = new Teleoperated(input_manager, drive, manip);
        this.auto = new Autonomous(drive, camera, manip);
        this.camera = AxisCamera.getInstance(Params.camera_IP);
        this.camera.writeResolution(Params.camera_resolution);
        this.camera.writeMaxFPS(Params.camera_FPS);
        
        this.auto_complete = false;
        
        // Set up Watchdog
        this.getWatchdog().setExpiration(Params.default_watchdog_time);
        
        // Stop timer
        init_timer.stop();
        
        System.out.println("=== DONE IN " + init_timer.get() + " MICROSECONDS ===");
    }

    public void disabledInit() {
        System.out.println("Going disabled.");
        this.getWatchdog().setEnabled(false);
    }

    public void disabledPeriodic() {
        // Nothing
    }

    public void teleopInit() {
        System.out.println("Going teleoperated.");
        this.getWatchdog().setEnabled(true);
        teleop.init();
    }

    /**
     * This function is called periodically during teleoperated mode
     */
    public void teleopPeriodic() {
        this.getWatchdog().feed();
        teleop.run();
    }

    public void teleopContinuous() {
    }
    
    public void autonomousInit()
    {
        System.out.println("Going autonomous.");
        this.getWatchdog().setEnabled(false);
        auto.init();
    }
    
    public void autonomousPeriodic()
    {
        while(!auto_complete) { auto_complete = auto.run(); }
    }
    
}
