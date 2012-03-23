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
        /*
        try {
            switch (this._stage)
            {
            case 0:
                this._manipulator.shoot(0.675);
                this.wait(1000);
                break;
            case 1:
                this._manipulator.intake(1);
                this.wait(1250);
                break;
            case 2:
                this._manipulator.intake(0);
                this.wait(500);
                break;
            case 3:
                this._manipulator.intake(1);
                this.wait(1250);
                break;
            case 4:
                this._manipulator.intake(0);
                this._manipulator.shoot(0.0);
                this._drive.tank_drive(0, 1);
                this.wait(1000);
                break;
            case 5:
                this._drive.tank_drive(0, 0);
                this._drive.tank_drive(-.5, -.5);
                this.wait(1750);
                break;
            case 6:
                this._drive.tank_drive(0, 0);
                this._manipulator.bridge(Relay.Value.kForward);
                this.wait(1500);
                break;
            case 7:
                this._manipulator.bridge(Relay.Value.kOff);
                this._manipulator.bridge(Relay.Value.kReverse);
                this.wait(1500);
                break;
            case 8:
                this._manipulator.bridge(Relay.Value.kOff);
                break;
            default:
                this._stage = 0;
                return;
            }
            this._stage++;
        }
        catch (InterruptedException ex) {
            System.out.println("Autonomous routine interupted:\n" + ex.getMessage() + "\nWill attempt to resume...");
        }
        *
        */

    }
}
