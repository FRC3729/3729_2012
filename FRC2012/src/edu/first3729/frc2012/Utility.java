/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

public class Utility {

    /**
     * @brief Clamp a value between a minimum and maximum
     *
     * @author Morgan Jones
     * @param value The value to clamp
     * @param min The minimum value to allow
     * @param max The maximum value to allow
     * @return The clamped value, in [min, max]
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * @brief Normalize input between -1.0 and 1.0. Also take into account min
     * and max values given by controller.
     *
     * @author Adam Bryant
     * @param val Input value to be normalized
     * @param min Minimum controller value. If using a joystick, supply -1.0.
     * @param max Maximum controller value. If using a joystick, supply 1.0.
     * @return The normalized value
     */
    public static double normalize(double val, double min, double max) {
        double retVal = 0.0;
        if (val < 0.0) {
            retVal = Math.abs(val) / min;
        } else if (val > 0.0) {
            retVal = Math.abs(val) / max;
        }
        return clamp(val, -1.0, 1.0);
    }

    /**
     * @brief Convert a boolean to an integer
     *
     * @param val The boolean to convert
     * @return An integer-ized boolean
     */
    public static int to_int(boolean val) {
        if (val) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @brief Exponential function that makes the controller more sensitive
     * toward the center and less so toward the outside
     *
     * @author Adam Bryant
     * @param x Input to be expo-ed
     * @param a Pre-defined exponentiation factor
     * @return The expo-ed value
     */
    public static double expo(double x, double a) {
        return (a * (x * x * x) + (1 - a) * x);
    }
}
