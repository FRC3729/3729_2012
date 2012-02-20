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
    private Joystick _controller2;
    protected int mode = 2;
    private int booleanInputs = 0;
    public static final int mecanum = 0;
    public static final int arcade_joy = 1;
    public static final int arcade_controller = 2;
    public static final int tank = 3;
    public static final int locked = 4;
    // Switch between 1.0 and Params.drive_creep_scale_factor
    private double scale_factor = 1.0;

    public Input() {
        this._joy0 = new Joystick(1);
        this._joy1 = new Joystick(2);
        this._controller = new Joystick(3);
        this._controller2 = new Joystick(4);
    }

    public void set_mode(int m) {
        this.mode = m;
    }

    public boolean check_button(int joystick, int button_id) {
        switch (joystick) {
            case 0:
                return this._joy0.getRawButton(button_id);
            case 1:
                return this._joy1.getRawButton(button_id);
            case 2:
                return this._controller.getRawButton(button_id);
            case 3:
                return this._controller2.getRawButton(button_id);
            default:
                return check_button(2, button_id);
        }
    }

    public int get_mode() {
        return mode;
    }

    /*
     * Modes: 0 = mecanum, 3 joysticks 1 = arcade, 2 joysticks 2 = arcade, 1
     * joystick 1 controller 3 = tank, 3 joysticks 4 = locked, no input
     */
    public double get_x() {
        switch (mode) {
            case mecanum:
                return Utility.normalize(this._joy1.getRawAxis(1), -1.0, 1.0);
            case arcade_joy:
                return Utility.expo(Utility.normalize(this._joy1.getX(), -1.0, 1.0), Params.JOYEXPO);
            case arcade_controller:
                return Utility.expo(Utility.normalize(this._controller.getX(), Params.XMIN, Params.XMAX), Params.XEXPO) * -1.0;
            case tank:
                return Utility.expo(Utility.normalize(this._joy0.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case locked:
                return 0;
            default:
                mode = 2;
                return get_x();
        }
    }

    public double get_y() {
        switch (mode) {
            case mecanum:
                return Utility.normalize(-this._joy0.getRawAxis(2), -1.0, 1.0);
            case arcade_joy:
                return Utility.expo(Utility.normalize(this._joy1.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case arcade_controller:
                return Utility.expo(Utility.normalize(this._controller.getY(), Params.YMIN, Params.YMAX), Params.YEXPO);
            case tank:
                return Utility.expo(Utility.normalize(this._joy1.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case locked:
                return 0;
            default:
                mode = 2;
                return get_y();
        }
    }

    public double get_z() {
        switch (mode) {
            case mecanum:
                return Utility.normalize(this._joy0.getRawAxis(1), -1.0, 1.0);
            case arcade_joy:
                return Utility.expo(Utility.normalize(this._joy0.getX(), -1.0, 1.0), Params.JOYEXPO);
            case arcade_controller:
                return Utility.expo(Utility.normalize(this._controller.getZ(), Params.ZMIN, Params.ZMAX), Params.YEXPO);
            case tank:
                return Utility.expo(Utility.normalize(this._joy0.getX(), -1.0, 1.0), Params.JOYEXPO);
            case locked:
                return 0;
            default:
                mode = 2;
                return get_z();
        }
    }

    public double get_w() {
        switch (mode) {
            case mecanum:
                return Utility.normalize(this._joy1.getRawAxis(2), -1.0, 1.0);
            case arcade_joy:
                return Utility.expo(Utility.normalize(this._joy0.getY(), -1.0, 1.0) * -1.0, Params.JOYEXPO);
            case arcade_controller:
                return Utility.expo(Utility.normalize(this._controller.getTwist(), Params.ROTMIN, Params.ROTMAX) * -1.0, Params.ROTEXPO);
            case tank:
                return Utility.expo(Utility.normalize(this._joy1.getX(), -1.0, 1.0), Params.JOYEXPO);
            case locked:
                return 0;
            default:
                mode = 2;
                return get_w();
        }
    }

    public double get_throttle(int joy) {
        switch (mode) {
            case mecanum:
            case arcade_joy:
                switch (joy) {
                    case 0:
                        return this._joy0.getThrottle();
                    case 1:
                        return this._joy1.getThrottle();
                    default:
                        joy = 0;
                        return get_throttle(joy);
                }
            case arcade_controller:
                return this._joy0.getThrottle();
            case tank:
                switch (joy) {
                    case 0:
                        return this._joy0.getThrottle();
                    case 1:
                        return this._joy1.getThrottle();
                    case 2:
                        return this._controller.getThrottle();  // 'Controller' is actually 3rd joystick
                    case 3:
                        return this._controller2.getThrottle();
                    default:
                        joy = 0;
                        return get_throttle(joy);
                }
            case locked:
                return 0;
            default:
                mode = 2;
                return get_throttle(0);
        }
    }

    public double get_throttle() {
        return get_throttle(0);
    }

    public double get_twist(int joy) {
        switch (mode) {
            case mecanum:
            case arcade_joy:
                switch (joy) {
                    case 0:
                        return this._joy0.getTwist();
                    case 1:
                        return this._joy1.getTwist();
                    default:
                        return get_twist(0);
                }
            case arcade_controller:
                return this._joy0.getTwist();
            case tank:
                switch (joy) {
                    case 0:
                        return this._joy0.getTwist();
                    case 1:
                        return this._joy1.getTwist();
                    case 2:
                        return this._controller.getTwist();  // 'Controller' is actually 3rd joystick
                    case 3:
                        return this._controller2.getTwist();
                    default:
                        return get_twist(0);
                }
            case locked:
                return 0;
            default:
                mode = 2;
                return get_twist(0);
        }
    }

    public double get_twist() {
        return get_twist(0);
    }
}
