/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.loopable;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.first3729.frc2012.loopable.FRCLoopable;
import edu.first3729.frc2012.periodic.gamemode.FRCGameMode;
import edu.first3729.frc2012.input.*;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Morgan
 */
public class FRCDrive implements FRCLoopable {
    // Parent FRCGameMode
    protected FRCGameMode _mode;
    
    // FRCInput system
    protected FRCInputInterLink _input;
    
    // FRCDrive system
    protected RobotDrive _drive;
    
    // Bridger
    protected Relay _bridger;
    
    protected DigitalInput _bridge_limit;
    
    // X, Y, and Z
    protected double _x, _y, _z;
    
    //! Which number the Drive Joystick is
    private static final int DRIVE_JOYSTICK = 1;
    
    private static final int BRIDGE_LIMIT_PORT = 2;
    
    //! Port # of the front left Jaguar on the drive train
    private static final int FL_JAGUAR = 1;
    
    //! Port # of the front right Jaguar on the drive train
    private static final int FR_JAGUAR = 3;
    
    //! Port # of the rear left Jaguar on the drive train
    private static final int RL_JAGUAR = 2;
    
    //! Port # of the rear right Jaguar on the drive train
    private static final int RR_JAGUAR = 4;
    
    //! Input scale factor
    private static final double INPUT_SCALE = 1.0;
    
    //! Tank drive dead-turn output factor
    private static final double TANK_SCALE = 0.75;
    
    public FRCDrive(FRCGameMode mode) {
        this._mode = mode;
    }
    
    public void setup() {
        this._input = new FRCInputInterLink(DRIVE_JOYSTICK);
        this._drive = new RobotDrive(FL_JAGUAR, RL_JAGUAR, FR_JAGUAR, RR_JAGUAR);
        this._bridge_limit = new DigitalInput(BRIDGE_LIMIT_PORT);
    }
    
    public void loop_periodic() {
        this.get_input();
        this.arcade_drive();
    }
    
    public void loop_continuous() {
        
    }
    
    /**
     * @brief Updates local input fields with values read from input devices
     */
    public void get_input() {
        // Get X and Y for the right stick
        this._x = -this._input.get_x() * INPUT_SCALE;
        this._y = -this._input.get_y() * INPUT_SCALE;
    }
    
    public void arcade_drive() {
        if (Math.abs(this._y) < 0.1)
            this._drive.tankDrive(-this._x * TANK_SCALE, this._x * TANK_SCALE);
        else
            this._drive.arcadeDrive(this._y, this._x);
    }
    
    public void drive() {
        if (Math.abs(this._y) < 0.05)
            this._drive.tankDrive(-this._x * TANK_SCALE, this._x * TANK_SCALE);
        else
            this._drive.drive(this._y, this._x);
    }
    
    public void tank_drive(double left, double right) {
        this._drive.tankDrive(-left, -right);
    }
    
    public void arcade_drive(double speed, double turn) {
        this._drive.arcadeDrive(speed, turn);
    }
}
