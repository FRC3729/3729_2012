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
    
    public Teleoperated(Input imanager, Drive drv)
    {
        this._input_manager = imanager;
        this._drive = drv;
    }
    
    public void init()
    {
        this._drive.lock_motors();
    }
    
    public void run()
    {
        // Check buttons and update setting accordingly
        this.updateBindings();
        switch (this._input_manager.getMode()) {
            case 'a':
                this._drive.drive_arcade(this._input_manager.getX(), this._input_manager.getY());
                break;
            case 'm':
                this._drive.drive_mecanum(this._input_manager.getX(), this._input_manager.getY(), this._input_manager.getZ());
                break;
            case 't':
                this._drive.drive_tank(this._input_manager.getX(), this._input_manager.getY());
                break;
        }
    }
    
    void updateBindings()
    {
        if (this._input_manager.checkButton(0, 6)) {
            this._input_manager.setMode('a');
        }
        if (this._input_manager.checkButton(0, 7)) {
            this._input_manager.setMode('m');
        }
        if (this._input_manager.checkButton(0, 10)) {
            this._input_manager.setMode('t');
        }
    }
    
}
