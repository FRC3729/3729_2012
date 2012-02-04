/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.first3729.frc2012;

/**
 *
 * @author matthewhaney
 */
public class Teleoperated
{
    private Input _input_manager;
    private Drive _drive;
    private Manipulator _manip;
    private double x, y, z;
    private boolean shoot_on, shoot_off, lift_on, lift_off, intake, bridge;
    private int input_left, input_right;
    
    public Teleoperated(Input imanager, Drive drv, Manipulator manip)
    {
        this._input_manager = imanager;
        this._manip = manip;
        this._drive = drv;
    }
    
    public void init()
    {
        this._drive.lock_motors();
    }
    
    public void test_buttons()
    {
        if (this._input_manager.checkButton(1, 1)) {
            this._drive.drive_mecanum(0, 1, 0);
        }
        else if (this._input_manager.checkButton(1, 4)) {
            this._drive.drive_mecanum(-1, 0, 0);
        }
        else if (this._input_manager.checkButton(1, 5)) {
            this._drive.drive_mecanum(1, 0, 0);
        }
        else if (this._input_manager.checkButton(1, 6)) {
            this._drive.drive_mecanum(0, 0, -1);
        }
        else if (this._input_manager.checkButton(1, 11)) {
            this._drive.drive_mecanum(0, 0, 1);
        }
    }
    
    public void run()
    {
        // Check buttons and update setting accordingly
        this.getInput();
        switch (this._input_manager.getMode()) {
            case 'a':
                this._drive.drive_arcade(this.x, this.y);
                break;
            case 'm':
                this._drive.drive_mecanum(this.x, this.y, this.z);
                break;
            case 't':
                this._drive.drive_tank(this.x, this.y);
                break;
            case 'l':
                this._drive.lock_motors();
        }
        this._manip.intake(intake);
        if (lift_on) { this._manip.lift(true); }
        else if (lift_off) { this._manip.lift(false); }
        if (shoot_on) { this._manip.shoot(true); }
        else if (shoot_off) { this._manip.shoot(false); }
        this._manip.bridge(bridge);
    }
    
    private void getInput()
    {
        this.x = this._input_manager.getX();
        this.y = this._input_manager.getY();
        this.z = this._input_manager.getZ();
        this.input_left = this._input_manager.getBooleanButtonInputs(0);
        this.input_right = this._input_manager.getBooleanButtonInputs(1);
        this.shoot_on = toBoolean((1 << 1) & input_left);
        this.shoot_off = toBoolean((1 << 2) & input_left);
        this.lift_on = toBoolean((1 << 3) & input_left);
        this.lift_off = toBoolean((1 << 4) & input_left);
        this.intake = toBoolean((1 << 0) & input_left);
         if (toBoolean((1 << 5) & input_left)) {
            this._input_manager.setMode('a');
        }
        if (toBoolean((1 << 6) & input_left)) {
            this._input_manager.setMode('m');
        }
        if (toBoolean((1 << 9) & input_left)) {
            this._input_manager.setMode('t');
        }
        if (toBoolean((1 << 10) & input_left)) {
            this._input_manager.setMode('l');
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
