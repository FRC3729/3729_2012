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
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Morgan
 */
public class FRCManipulator implements FRCLoopable {
    // Parent game mode
    protected FRCGameMode _mode;
    
    protected FRCInputXbox _input;
    
    protected DriverStationLCD dsl;
    
    // Relays!
    protected Relay _elevator, _intake, _bridger;
    
    private Jaguar _shooter_top;
    private Jaguar _shooter_bottom;
    
    private double SHOOTER_SPEED = 0.5;
    private static final double SHOOTER_SPEED_INCREMENT = 0.05;
    
    private static final int MANIP_JOYSTICK = 2;
    private static final int INTAKE_PORT = 1;
    private static final int ELEVATOR_PORT = 2;
    private static final int SHOOTER_TOP_PORT = 5;
    private static final int SHOOTER_BOTTOM_PORT = 6;
    
    //! The bridge relay port
    private static final int BRIDGE_RELAY_PORT = 3;
    private static final int BRIDGE_LIMIT_PORT = 1;
    
    private boolean shooter_state = false, shooter_edge = false;
    private double intake_state = 0;
    private boolean intake_edge = false;
    private int elevator_state = 0, bridge_state = 0;
    private boolean back_edge = false;
    private boolean start_edge = false;
    // PROLLY NOT THIS
    private boolean net_state = true;
    
    // Digital inputs for intake sensor and bridge limit switch.
    protected DigitalInput _bridge_limit;
    
    public FRCManipulator(FRCGameMode mode) {
        this._mode = mode;
    }
        
    public void setup() {
        this._intake = new Relay(this.INTAKE_PORT);
        this._elevator = new Relay(this.ELEVATOR_PORT);
        this._shooter_top = new Jaguar(this.SHOOTER_TOP_PORT);
        this._shooter_bottom = new Jaguar(this.SHOOTER_BOTTOM_PORT);
        this._bridger = new Relay(BRIDGE_RELAY_PORT);
        this._bridger.setDirection(Relay.Direction.kBoth);
        this.dsl = DriverStationLCD.getInstance();
        
        // Don't lift stuff
        this._elevator.setDirection(Relay.Direction.kBoth);
        this.lift(0);

        // Disable intake
        this._intake.setDirection(Relay.Direction.kBoth);
        this.intake(0);
        
        this._input = new FRCInputXbox(MANIP_JOYSTICK);
        this._bridge_limit = new DigitalInput(1);
        // Don't shoot, just to be 100% safe
        this.shoot(0);
    }
    
    public void loop_periodic() {
        this.get_input();
        this.lift(this.elevator_state);
        this.intake(this.intake_state);
        this.shoot(this.shooter_state);
        this.bridge(this.bridge_state);
    }
    
    public void loop_continuous() {
        
    }
    
    public void get_input() {
        dsl.println(DriverStationLCD.Line.kUser2, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.println(DriverStationLCD.Line.kUser3, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.println(DriverStationLCD.Line.kUser4, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.println(DriverStationLCD.Line.kUser5, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.println(DriverStationLCD.Line.kUser6, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.println(DriverStationLCD.Line.kMain6, 1, "Shooter speed: " + SHOOTER_SPEED);
        dsl.updateLCD();
        // Shooter toggle mapped to left trigger
        if (this._input.get_button(FRCInputXbox.L_BUTTON) && !shooter_edge) {
            shooter_state = !shooter_state;
            shooter_edge = true;
        }
        else if (!this._input.get_button(FRCInputXbox.L_BUTTON)) {
            shooter_edge = false;
        }
        
        // Intake toggle mapped to right trigger
        this.intake_state = this._input.get_triggers();
        
        // Elevator on = Y, elevator off = A
        if (this._input.get_button(FRCInputXbox.Y_BUTTON)) {
            this.elevator_state = 1;
        } else if (this._input.get_button(FRCInputXbox.L3_BUTTON)) {
            this.elevator_state = -1;
        } else if (this._input.get_button(FRCInputXbox.A_BUTTON)) {
            this.elevator_state = 0;
        }
        
        // Bridge on = X, bridge off = B
        if (this._input.get_button(FRCInputXbox.X_BUTTON)) {
            this.bridge_state = 1;
        } else if (this._input.get_button(FRCInputXbox.B_BUTTON)) {
            this.bridge_state = -1;
        } else {
            this.bridge_state = 0;
        }
        
        // Back button - reduce shooter speed
        if (this._input.get_button(FRCInputXbox.BACK_BUTTON) && !back_edge) {
            SHOOTER_SPEED -= SHOOTER_SPEED_INCREMENT;
            back_edge = true;
        }
        else if (!this._input.get_button(FRCInputXbox.BACK_BUTTON)) {
            back_edge = false;
        }
        
        // Start button - reduce shooter speed
        if (this._input.get_button(FRCInputXbox.START_BUTTON) && !start_edge) {
            SHOOTER_SPEED += SHOOTER_SPEED_INCREMENT;
            start_edge = true;
        }
        else if (!this._input.get_button(FRCInputXbox.START_BUTTON)) {
            start_edge = false;
        }
        
        // Shooter speed presets - axes 4 and 5
        if (this._input.get_axis(4) < -0.25) {
            SHOOTER_SPEED = 0.65;
        } else if (this._input.get_axis(4) > 0.25) {
            SHOOTER_SPEED = 0.7;
        } else if (this._input.get_axis(5) < -0.25) {
            SHOOTER_SPEED = 0.625;
        } else if (this._input.get_axis(5) > 0.25) {
            SHOOTER_SPEED = 0.725;
        }
               
    }
    
    public void bridge(int state) {
        if (state == 1 && !_bridge_limit.get()) {
            this.bridge(Relay.Value.kForward);
        } else if (state == -1) {
            this.bridge(Relay.Value.kReverse);
        } else {
            this.bridge(Relay.Value.kOff);
        }
    }

    public void bridge(Relay.Value state) {
        this._bridger.set(state);
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

    public void intake(double state) {
        // Turn on until limit switch is pressed, then off (ideally)
        if (state > 0) {
            this.intake(Relay.Value.kForward);
        } else if (state < 0) {
            this.intake(Relay.Value.kReverse);
        } else {
            this.intake(Relay.Value.kOff);
        }
    }
    
    public void intake(Relay.Value state) {
        this._intake.set(state);
    }
    
    public void shoot(boolean state) {
        if (state) {
            this.shoot(SHOOTER_SPEED);
        } else {
            this.shoot(0.0);
        }
    }
    
    public void shoot(double speed) {
        this._shooter_top.set(speed);
        this._shooter_bottom.set(speed);
    }
}
