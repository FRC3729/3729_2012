/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @class Manipulator @brief Class that abstracts manipulator
 *
 * Manages assorted motor controllers to put balls in nets.   Mmmmm.  Oh yeah.
 */
public class Manipulator {

    private Victor shooter1;
    private Victor shooter2;
    protected Relay elevator;
    protected Relay bridge;
    protected Relay intake;
    protected Relay net;

    public Manipulator() {
        shooter1 = new Victor(Params.shooter1_victor_port);
        shooter2 = new Victor(Params.shooter2_victor_port);
        elevator = new Relay(Params.elevator_relay_port);
        bridge = new Relay(Params.bridge_relay_port);
        intake = new Relay(Params.intake_relay_port);
        net = new Relay(Params.net_relay_port);
    }

    public void init() {
        elevator.setDirection(Relay.Direction.kBoth);
        elevator.set(Relay.Value.kOff);
        bridge.setDirection(Relay.Direction.kBoth);
        bridge.set(Relay.Value.kOff);
        intake.setDirection(Relay.Direction.kBoth);
        intake.set(Relay.Value.kOff);
    }

    public void shoot(boolean state) {
        if (state) {
            shooter1.set(Params.shooter1_speed);
            shooter2.set(Params.shooter2_speed);
        } else {
            shooter1.set(0.0);
            shooter2.set(0.0);
        }

    }

    public void lift(boolean state) {
        if (state) {
            elevator.set(Relay.Value.kForward);
        } else {
            elevator.set(Relay.Value.kOff);
        }
    }

    public void intake(boolean state) {
        // Turn on until limit switch is pressed, then off
        if (state) {
            intake.set(Relay.Value.kForward);
        } else {
            intake.set(Relay.Value.kOff);
        }

    }

    public void bridge(Relay.Value state) {
        bridge.set(state);
    }

    public void lift_net(Relay.Value state) {
        net.set(state);
    }
}
