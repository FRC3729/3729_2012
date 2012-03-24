/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012.input;

import edu.first3729.frc2012.utility.*;

import edu.wpi.first.wpilibj.Joystick;

public class FRCInputInterLink extends FRCInput {
    //! Left and right stick
    private static final int LEFT_STICK = 0;
    private static final int RIGHT_STICK = 1;
    
    private static final double X_EXPONENT = 0.4;
    private static final double Y_EXPONENT = 0.4;
    
    private static final double YMAX = 0.4480142593383789;
    private static final double YMIN = -0.4447765871909697;
    private static final double XMAX = 0.5488231448903843;
    private static final double XMIN = -0.5244430541992188;
    
    public FRCInputInterLink(int number) {
        super(number);
    }
    
    public double get_x() {
        return FRCUtility.expo(FRCUtility.normalize(super.get_x(), XMIN, XMAX), X_EXPONENT) * -1.0;
    }

    public double get_y() {
         return FRCUtility.expo(FRCUtility.normalize(super.get_y(), YMIN, YMAX), Y_EXPONENT);
    }

    public double get_z() {
         return 0;
    }

    public double get_w() {
         return 0;
    }
    
    public void left_stick() {
        this._joy.setAxisChannel(Joystick.AxisType.kX, LEFT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kY, LEFT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kZ, LEFT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kThrottle, LEFT_STICK);
    }
    
    public void right_stick() {
        this._joy.setAxisChannel(Joystick.AxisType.kX, RIGHT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kY, RIGHT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kZ, RIGHT_STICK);
        this._joy.setAxisChannel(Joystick.AxisType.kThrottle, RIGHT_STICK);
    }
}
