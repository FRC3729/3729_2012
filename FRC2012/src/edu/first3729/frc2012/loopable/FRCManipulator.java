/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.loopable;

import edu.first3729.frc2012.periodic.gamemode.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Morgan
 */
public class FRCManipulator implements FRCLoopable {
    // Parent game mode
    protected FRCGameMode _mode;
    
    // Relays!
    protected Relay _elevator, _intake;
    
    private Victor _shooter_top;
    private Victor _shooter_bottom;
    
    // Digital inputs for intake sensor and bridge limit switch.
    protected DigitalInput _intake_sensor, _bridge_limit;
    
    public FRCManipulator(FRCGameMode mode) {
        this._mode = mode;
    }
        
    public void setup() {
        // Don't lift stuff
        this._elevator.setDirection(Relay.Direction.kBoth);
        this.lift(false);

        // Disable intake
        this._intake.setDirection(Relay.Direction.kBoth);
        this.intake(false);
        
        // Don't shoot, just to be 100% safe
        this.shoot(0.0);
    }
    
    public void loop_periodic() {
        
    }
    
    public void loop_continuous() {
        
    }
    
    // Public instance methods
    public boolean get_intake_sensor() {
        return this._intake_sensor.get();
    }
    
    public boolean get_bridge_limit() {
        return this._bridge_limit.get();
    }
    
    public void lift(boolean state) {
        if (state) {
            this.lift(Relay.Value.kForward);
        } else {
            this.lift(Relay.Value.kOff);
        }
    }
    
    public void lift(Relay.Value state) {
        this._elevator.set(state);
    }

    public void intake(boolean state) {
        // Turn on until limit switch is pressed, then off
        if (state) {
            this.lift(Relay.Value.kForward);
        } else {
            this.lift(Relay.Value.kOff);
        }
    }
    
    public void intake(Relay.Value state) {
        this._intake.set(state);
    }
    
    public void shoot(boolean state) {
        if (state) {
            this.shoot(1.0);
        } else {
            this.shoot(0.0);
        }
    }
    
    public void shoot(double speed) {
        this._shooter_top.set(speed);
        this._shooter_bottom.set(speed);
    }
}
