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
import edu.first3729.frc2012.input.*;
import edu.first3729.frc2012.loopable.*;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author matthewhaney
 */
public class FRCGameModeAutonomous extends FRCGameMode {
    private AxisCamera _cam;
    
    private int state = 0;
    
    private Timer _timer;
    
    public FRCGameModeAutonomous(FRCRobot robot) {
        // Initialize superclass
        super(robot);
    }
    
    public void init() {
        this._timer = new Timer();
        _timer.stop();
        _timer.reset();
        _timer.start();
    }
    
    public void loop_continuous() {
       this._drive.loop_continuous();
       this._manipulator.loop_continuous();
    }
    
    public void loop_periodic() {
        if (this._timer.get() < 0.5) {
            state = 1;
        } else if (this._timer.get() < 1.75) {
            state = 2;
        } else if (this._timer.get() < 2.5) {
            state = 3;
        } else if (this._timer.get() < 3.75) {
            state = 4;
        } else if (this._timer.get() < 4.5) {
            state = 5;
        } else if (this._timer.get() < 5) {
            state = 6;
        } else if (this._timer.get() < 6) {
            state = 7;
        } else if (this._timer.get() < 7.5) {
            state = 8;
        } else if (this._timer.get() < 8) {
            state = 9;
        } else if (this._timer.get() < 9) {
            state = 10;
        } else {
            state = 0;
        }
        
        switch (state) {
            case 1:
                this._manipulator.shoot(0.625, 0.625);
                break;
            case 2:
                this._manipulator.intake(1);
                break;
            case 3:
                this._manipulator.intake(0);
                break;
            case 4:
                this._manipulator.intake(1);
                break;
            case 5:
                this._manipulator.intake(0);
                this._manipulator.shoot(0, 0);
                break;
                /*
            case 6:
                this._drive.tank_drive(-0.5, 0);
                break;
            case 7:
                this._drive.tank_drive(-0.5, -0.5);
                break;
            case 8:
                this._drive.tank_drive(0, 0);
                this._manipulator.bridge(1);
                break;
            case 9:
                this._manipulator.bridge(0);
                break;
                * */
            default:
                this._manipulator.bridge(0);
                this._drive.tank_drive(0, 0);
        }
        
    }
}
