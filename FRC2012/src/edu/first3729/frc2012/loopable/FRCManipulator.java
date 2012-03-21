/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.loopable;

import edu.first3729.frc2012.input.FRCInput;
import edu.first3729.frc2012.input.FRCInputAttack3;
import edu.first3729.frc2012.input.FRCInputXbox;
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
    
    protected FRCInput _input;
    
    // Relays!
    protected Relay _elevator, _intake;
    
    private Victor _shooter_top;
    private Victor _shooter_bottom;
    
    private static final int MANIP_JOYSTICK = 2;
    
    private boolean shooter_state = false, shooter_edge = false;
    private boolean intake_state = false, intake_edge = false;
    private int elevator_state = 0;
    // PROLLY NOT THIS
    private boolean net_state = true;
    
    // Digital inputs for intake sensor and bridge limit switch.
    protected DigitalInput _intake_sensor;
    
    public FRCManipulator(FRCGameMode mode) {
        this._mode = mode;
    }
        
    public void setup() {
        // Don't lift stuff
        this._elevator.setDirection(Relay.Direction.kBoth);
        this.lift(0);

        // Disable intake
        this._intake.setDirection(Relay.Direction.kBoth);
        this.intake(false);
        
        this._input = new FRCInputXbox(MANIP_JOYSTICK);
        
        // Don't shoot, just to be 100% safe
        this.shoot(0.0);
    }
    
    public void loop_periodic() {
        this.get_input();
        this.lift(this.elevator_state);
        this.intake(this.intake_state);
        this.shoot(this.shooter_state);
    }
    
    public void loop_continuous() {
        
    }
    
    public void get_input() {
        // Shooter toggle mapped to left trigger
        if (this._input.get_button(FRCInputXbox.LEFT_TRIGGER) && shooter_edge != shooter_state) {
            this.shooter_state = !this.shooter_state;
            shooter_edge = shooter_state;
        }
        
        // Intake toggle mapped to right trigger
        if (this._input.get_button(FRCInputXbox.RIGHT_TRIGGER) && intake_edge != intake_state) {
            this.intake_state = !this.shooter_state;
            intake_edge = intake_state;
        }
        
        // Elevator on = Y, elevator off = A
        if (this._input.get_y() > 0.25) {
            this.elevator_state = 1;
        } else if (this._input.get_y() < -0.25) {
            this.elevator_state = -1;
        } else
            this.elevator_state = 0;
        
        // Net on = X, net off = B
        if (this._input.get_button(FRCInputXbox.X_BUTTON)) {
            this.net_state = true;
        } else if (this._input.get_button(FRCInputXbox.B_BUTTON)) {
            this.net_state = false;
        }
               
    }
    
    // Public instance methods
    public boolean get_intake_sensor() {
        return this._intake_sensor.get();
    }
    
    public void lift(int state) {
        switch (state){
            case 1:
                this.lift(Relay.Value.kForward);
                break;
            case 0:
                this.lift(Relay.Value.kOff);
                break;
            case -1:
                this.lift(Relay.Value.kReverse);
                break;
            default:
                this.lift(Relay.Value.kOff);
                break;
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
