/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author matthewhaney
 */
public class Teleoperated
{
    private Input _input_manager;
    private Drive _drive;
    private Manipulator _manip;
    private double x = 0.0, y = 0.0, z = 0.0, left = 0.0, right = 0.0;
    private boolean net = false, shoot = false, lift = false, intake = false, bridge_down = false, bridge_up = false;
    private boolean shoot_old = false, lift_old = false;
    private int input_left = 0, input_right = 0, input_controller = 0;
    
    public Teleoperated(Input imanager, Drive drv, Manipulator manip)
    {
        this._input_manager = imanager;
        this._manip = manip;
        this._drive = drv;
    }
    
    public void init()
    {
        this._drive.lock_motors();
        this._manip.init();
    }
    
    public void run()
    {
        // Check buttons and update setting accordingly
        this.getInput(this._input_manager.getMode());
        switch (this._input_manager.getMode()) {
            case 0:
                this._drive.drive_mecanum(this.x, this.y, this.z);
            case 1:
            case 2:
                this._drive.drive_arcade(this.x, this.y);
                break;
            case 3:
                this._drive.drive_tank(this.left, this.right);
                break;
            case 4:
                this._drive.lock_motors();
        }
        this._manip.intake(intake);
        if (lift != lift_old) { this._manip.lift(lift); lift_old = lift; }
        if (shoot != shoot_old) { this._manip.shoot(shoot); shoot_old = shoot; }
        if (bridge_up) { this._manip.bridge(Relay.Value.kForward); }
        else if (bridge_down) { this._manip.bridge(Relay.Value.kReverse); }
        else { this._manip.bridge(Relay.Value.kOff); }
        if (net) { this._manip.lift_net(Relay.Value.kForward); }
        else { this._manip.lift_net(Relay.Value.kOff); }
    }
    
    private void getInput(int mode)
    {
        this.x = this._input_manager.getX();
        this.y = this._input_manager.getY();
        this.z = this._input_manager.getZ();
        this.input_left = this._input_manager.getBooleanButtonInputs(0);
        this.input_right = this._input_manager.getBooleanButtonInputs(1);
        switch (mode) {
            case 0:  // Mecanum drive, we'll disable it for now
                getInput(1);
                break;
            default:
            case 1:  // Arcade drive, two joysticks
                this.intake = toBoolean((1 << 0) & input_left);
                this.shoot = toBoolean((1 << 1) & input_left);
                this.bridge_down = toBoolean((1 << 2) & input_left);
                this.lift = toBoolean((1 << 3) & input_left);
                this.bridge_up = toBoolean((1 << 4) & input_left);
                this.net = toBoolean((1 << 7) * input_left);
                break;
        }
        if (toBoolean((1 << 5) & input_right)) {
            this._input_manager.setMode(1);
        }
        /*
        if (toBoolean((1 << 6) & input_right)) {
            this._input_manager.setMode(2);
        }
        if (toBoolean((1 << 9) & input_right)) {
            this._input_manager.setMode(3);
        }
        */
        if (toBoolean((1 << 10) & input_right)) {
            this._input_manager.setMode(4);
        }
        System.out.println("X: " + x + " Y: " + y + "Z: " + z);
        System.out.println(Integer.toBinaryString(input_left));
        System.out.println(Integer.toBinaryString(input_right));
    }
    
    private boolean toBoolean(long thingummy)
    {
        if (thingummy == 0L)
            return false;
        else
            return true;
    }
    
}
