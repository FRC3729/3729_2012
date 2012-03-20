 /*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012.periodic.gamemode;

/**
 * @file FRCGameModeTeleoperated.java
 */

import edu.first3729.frc2012.FRCRobot;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @class FRCGameModeTeleoperated
 * @brief Class that runs robot during teleoperated period
 *
 * Manages instances of FRCDrive, Input, and ManipulatorCompetition classes. Gets appropriate
 * input based on controller layout mode and sets outputs accordingly.
 */
public class FRCGameModeTeleoperated extends FRCGameMode {    
    /*private double x = 0.0, y = 0.0, z = 0.0, left = 0.0, right = 0.0, scale_factor = 0.0;
    private boolean polarity = false, net_down = false, net_up = false, intake = false, bridge_down = false, bridge_up = false;
    private boolean shoot_on = false, shoot_off = false, lift_on = false, lift_off = false;*/

    public FRCGameModeTeleoperated(FRCRobot robot) {
        super(robot);
    }

    /**
     * @brief Initializes manipulator, locks drive, locks input
     */
    public void setup() {
    }

    /**
     * @brief Runs FRCGameModeTeleoperated period
     * 
     * Runs FRCGameModeTeleoperated period
     */
    public void loop_periodic() {
        /* Update input fields
        this.getInput();
        
        // FRCDrive teh robot
        this._drive.arcadeDrive(this.x, this.y);

        /* If intake limit switch is hit, turn off intake relay
        if (!this.intake_limit.get()) {
            for (int i = 0; i < 10000; i++) { continue; }
            this._manipulator.intake(Relay.Value.kOff);
        }
        // Intake control   
        if (this.intake) {
            this._manipulator.intake(Relay.Value.kForward);
        }
        
        if (!this._manipulator.get_intake_sensor()) {
            this._manipulator.intake(false);
        }
        if (this.intake) {
            if (!this.shoot_on)
                this._manipulator.shoot(true);
            this._manipulator.intake(true);
        }

        // Gist of the above 2 'if' statements: when intake button pressed,
        // keep relay running until limit switch is pressed.

        // Toggle elevator
        if (lift_on) {
            this._manipulator.lift(true);
        } else if (lift_off) {
            this._manipulator.lift(false);
        }

        // Toggle shooter
        if (shoot_on) {
            this._manipulator.shoot(true);
        } else if (shoot_off) {
            this._manipulator.shoot(false);
        }

        // If up and down both requested, do nothing
        if (bridge_up && bridge_down) {
            this._manipulator.bridge(Relay.Value.kOff);
        }

        // Bridge relay control
        if (bridge_up && !this._manipulator.get_bridge_limit()) {
            this._manipulator.bridge(Relay.Value.kForward);
        } else if (bridge_down) {
            this._manipulator.bridge(Relay.Value.kReverse);
        } else {
            this._manipulator.bridge(Relay.Value.kOff);
        }

        // If up and down both requested, do nothing
        if (bridge_up && bridge_down) {
            this._manipulator.bridge(Relay.Value.kOff);
        }*/
        
        // Run drive and manipulator loops
        this._drive.loop_periodic();
        this._manipulator.loop_periodic();
    }
    
    public void loop_continuous() {
        // Run continuous drive and manipulator loops
        this._drive.loop_continuous();
        this._manipulator.loop_continuous();
    }
}