/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012.periodic.gamemode;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;

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
    
    public FRCGameModeAutonomous(FRCRobot robot) {
        // Initialize superclass
        super(robot);
    }
    
    public void loop_continuous() {
       this._drive.loop_continuous();
       this._manipulator.loop_continuous();
    }
    
    public void loop_periodic() {
        /*try {
            switch (this._stage)
            {
            case 0:
                this._manipulator.shoot(1.0);
                this.wait(1000);
                break;
            case 1:
                this._manipulator.intake(true);
                this.wait(1250);
                break;
            case 2:
                this._manipulator.intake(false);
                this.wait(500);
                break;
            case 3:
                this._manipulator.intake(true);
                this.wait(1250);
                break;
            case 4:
                this._manipulator.intake(false);
                this._manipulator.shoot(0.0);
                this._drive.tankDrive(0, 1);
                this.wait(1000);
                break;
            case 5:
                this._drive.tankDrive(0, 0);
                this._drive.tankDrive(-.5, -.5);
                this.wait(1750);
                break;
            case 6:
                this._drive.tankDrive(0, 0);
                this._drive.bridge(Relay.Value.kForward);
                this.wait(1500);
                break;
            case 7:
                this._drive.bridge(Relay.Value.kOff);
                this._drive.bridge(Relay.Value.kReverse);
                this.wait(1500);
                break;
            case 8:
                this._drive.bridge(Relay.Value.kOff);
                break;
            default:
                this._stage = 0;
                return;
            }

            this._stage++;
        }*/
        
        this._drive.loop_periodic();
        this._manipulator.loop_periodic();
    }
}
