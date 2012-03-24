/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012.periodic.gamemode;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.first3729.frc2012.FRCRobot;
import edu.first3729.frc2012.FRCParams;
import edu.first3729.frc2012.input.*;
import edu.first3729.frc2012.loopable.*;

/**
 *
 * @author matthewhaney
 */
public class FRCGameModeAutonomous extends FRCGameMode {
    private AxisCamera _cam;
    
    private int _stage = 0;
    
    private Timer timer;
    
    public FRCGameModeAutonomous(FRCRobot robot) {
        // Initialize superclass
        super(robot);
        this.timer = new Timer();
    }
    
    public void init() {
        timer.stop();
        timer.reset();
        timer.start();
    }
    
    public void loop_continuous() {
       this._drive.loop_continuous();
       this._manipulator.loop_continuous();
    }
    
    public void loop_periodic() {
        /*
        if (this.timer.get() < .5) { 
            this._drive.tank_drive(-0.5, 0);
        } else if (this.timer.get() < 1.5) {
            System.out.println("More aStuff");
            this._drive.tank_drive(-0.5, -0.5);
        } else if (this.timer.get() < 2) {
            this._drive.tank_drive(0, 0);
        } else if (this.timer.get() < 3) {
            this._manipulator.bridge(1);
        } else if (this.timer.get() < 3.5) {
            this._manipulator.bridge(0);
        } else {
            // WOOOOOO!
        }
        * 
        */
    }
}
