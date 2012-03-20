/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Morgan
 */
public class FRCInput {
    protected Joystick _joy;
    
    public FRCInput(int number) {
        this._joy = new Joystick(number);
    }
    
    public double get_x() {
        return this._joy.getX();
    }
    
    public double get_y() {
        return this._joy.getY();
    }
    
    public double get_z() {
        return this._joy.getZ();
    }

    public double get_throttle() {
        return this._joy.getThrottle();
    }
    
    public boolean get_button(int id) {
        return this._joy.getRawButton(id);
    }
}
