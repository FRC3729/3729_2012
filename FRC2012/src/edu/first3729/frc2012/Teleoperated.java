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
    private DigitalInput intake_limit;
    private double x = 0.0, y = 0.0, z = 0.0, left = 0.0, right = 0.0;
    private boolean intake_continue = false, net = false, shoot = false, lift = false, intake = false, bridge_down = false, bridge_up = false;
    private boolean shoot_old = false, lift_old = false;
    private int input_left = 0, input_right = 0, input_controller = 0;
    
    public Teleoperated(Input imanager, Drive drv, Manipulator manip)
    {
        this._input_manager = imanager;
        this._manip = manip;
        this._drive = drv;
        this.intake_limit = new DigitalInput(Params.intake_limit_digin_port);
    }
    
    public void init()
    {
        this._drive.lock_motors();
        this._manip.init();
    }
    
    public void run()
    {
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
        if (!this.intake_limit.get()) {
            this._manip.intake(Relay.Value.kOff);
        }
        if (this.intake) {
            this._manip.intake(Relay.Value.kForward);
        }
        if (lift && lift != lift_old) { this._manip.lift(lift); lift_old = lift; }
        if (shoot && shoot != shoot_old) { this._manip.shoot(shoot); shoot_old = shoot; }
        if (bridge_up) { this._manip.bridge(Relay.Value.kForward); }
        else if (bridge_down) { this._manip.bridge(Relay.Value.kReverse); }
        else { this._manip.bridge(Relay.Value.kOff); }
        if (net) { this._manip.lift_net(Relay.Value.kForward); }
        else { this._manip.lift_net(Relay.Value.kOff); }
    }
    
    public void getInput(int mode)
    {
        this.x = this._input_manager.getX();
        this.y = this._input_manager.getY();
        this.z = this._input_manager.getZ();
        this.input_left = this._input_manager.getBooleanButtonInputs(0);
        this.input_right = this._input_manager.getBooleanButtonInputs(1);
        this.input_controller = this._input_manager.getBooleanButtonInputs(2);
        switch (mode) {
            case 0:  // Mecanum drive, we'll disable it for now
                getInput(1);
                break;
            default:
            case 1:  // Arcade drive, two joysticks
                this.intake = this._input_manager.checkButton(0, 1);
                this.shoot = this._input_manager.checkButton(0, 2);
                this.bridge_down = this._input_manager.checkButton(0, 4);
                this.lift = this._input_manager.checkButton(0, 3);
                this.bridge_up = this._input_manager.checkButton(0, 5);
                this.net = this._input_manager.checkButton(0, 8);
                break;
            case 4:  // Locked controls, nothing to do here
                this.intake = this.shoot = this.bridge_up = this.bridge_down = this.lift = this.net = false;
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
