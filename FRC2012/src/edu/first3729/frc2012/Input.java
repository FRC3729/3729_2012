/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.Joystick;

public class Input {

    private Joystick _joy0;
    private Joystick _joy1;
    private Joystick _controller;
    
    // Switch between 1.0 and Params.drive_creep_scale_factor
    private double scale_factor = 1.0;

    public Input() {
        this._joy0 = new Joystick(1);
        this._joy1 = new Joystick(2);
        this._controller = new Joystick(3);
    }

    public boolean check_button(int joystick, int button_id) {
        switch (joystick) {
            case 0:
                return this._joy0.getRawButton(button_id);
            case 1:
                return this._joy1.getRawButton(button_id);
            case 2:
                return this._controller.getRawButton(button_id);
            default:
                return check_button(2, button_id);
        }
    }

    /*
     * Modes: 0 = mecanum, 3 joysticks 1 = arcade, 2 joysticks 2 = arcade, 1
     * joystick 1 controller 3 = tank, 3 joysticks 4 = locked, no input
     */
    public double get_x() {
        return Utility.expo(Utility.normalize(this._controller.getX(), Params.XMIN, Params.XMAX), Params.XEXPO) * -1.0;
    }

    public double get_y() {
         return Utility.expo(Utility.normalize(this._controller.getY(), Params.YMIN, Params.YMAX), Params.YEXPO);
    }

    public double get_z() {
         return Utility.expo(Utility.normalize(this._controller.getZ(), Params.ZMIN, Params.ZMAX), Params.YEXPO);
    }

    public double get_w() {
         return Utility.expo(Utility.normalize(this._controller.getTwist(), Params.ROTMIN, Params.ROTMAX) * -1.0, Params.ROTEXPO);
    }

    public double get_throttle(int joy) {
         switch (joy) {
            case 0:
                return this._joy0.getThrottle();
            case 1:
                return this._joy1.getThrottle();
            case 2:
                return this._controller.getThrottle();
            default:
                joy = 0;
                return get_throttle(joy);
         }
    }

    public double get_throttle() {
        return get_throttle(0);
    }

    public double get_twist(int joy) {
        switch (joy) {
            case 0:
                return this._joy0.getTwist();
            case 1:
                return this._joy1.getTwist();
            case 2:
                return this._controller.getTwist();
            default:
                joy = 0;
                return get_twist(joy);
         }
    }

    public double get_twist() {
        return get_twist(0);
    }
}
