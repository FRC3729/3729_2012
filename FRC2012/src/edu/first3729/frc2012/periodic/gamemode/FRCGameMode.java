/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.periodic.gamemode;

import edu.first3729.frc2012.FRCRobot;
import edu.first3729.frc2012.FRCParams;
import edu.first3729.frc2012.loopable.*;
import edu.first3729.frc2012.input.*;

/**
 *
 * @author Morgan
 */
public abstract class FRCGameMode implements FRCLoopable {
    // Main robot class
    protected FRCRobot _robot;
    
    // Common drive system
    protected FRCDrive _drive;
    
    // And a common manipulator
    protected FRCManipulator _manipulator;
    
    public FRCGameMode(FRCRobot robot) {
        this._robot = robot;
    }
    
    public static FRCGameMode to_autonomous(FRCGameMode mode) {
        return to_autonomous(mode, mode._robot);
    }
    
    public static FRCGameMode to_autonomous(FRCGameMode mode, FRCRobot robot) {
        FRCGameMode ret = new FRCGameModeAutonomous(robot);
        if (mode != null) {
            ret._drive = mode._drive;
            ret._manipulator = mode._manipulator;
        } else {
            ret._drive = new FRCDrive(ret);
            ret._manipulator = new FRCManipulator(ret);
            ret.setup();
        }
        return ret;
    }
    
    public static FRCGameMode to_teleoperated(FRCGameMode mode) {
        return to_teleoperated(mode, mode._robot);
    }
    
    public static FRCGameMode to_teleoperated(FRCGameMode mode, FRCRobot robot) {
        FRCGameMode ret = new FRCGameModeTeleoperated(robot);
        if (mode != null) {
            ret._drive = mode._drive;
            ret._manipulator = mode._manipulator;
        } else {
            ret._drive = new FRCDrive(ret);
            ret._manipulator = new FRCManipulator(ret);
            ret.setup();
        }
        ret._manipulator.lift(0);
        return ret;
    }
    
    public static FRCGameMode to_disabled(FRCGameMode mode) {
        return to_disabled(mode, mode._robot);
    }
    
    public static FRCGameMode to_disabled(FRCGameMode mode, FRCRobot robot) {
        FRCGameMode ret = new FRCGameModeDisabled(robot);
        if (mode != null) {
            ret._drive = mode._drive;
            ret._manipulator = mode._manipulator;
        } else {
            ret._drive = new FRCDrive(ret);
            ret._manipulator = new FRCManipulator(ret);
            ret.setup();
        }
        return ret;
    }
    
    public void init() {
        
    }
    
    public void setup() {
        this._drive.setup();
        this._manipulator.setup();
    }
}
