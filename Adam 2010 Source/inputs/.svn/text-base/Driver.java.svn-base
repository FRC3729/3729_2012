/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inputs;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author adam
 */
public class Driver
{
    private final Joystick joy;

    private static final double YCENTER = 0.03125;
    private static final double ROTCENTER = 0.0156;
    
    private static final double XMIN = -0.641;
    private static final double XMAX = 0.648;
    private static final double YMIN = -0.57 - YCENTER;
    private static final double YMAX = 0.641 - YCENTER;
    private static final double ZMIN = -0.54;
    private static final double ZMAX = 0.63;
    private static final double ROTMIN = -0.64 - ROTCENTER;
    private static final double ROTMAX = 0.68 - ROTCENTER;

    private static final double XEXPO = 0.4;
    private static final double YEXPO = 0.4;
    private static final double ROTEXPO = 0.6;

    public Driver(Joystick joy)
    {
        this.joy = joy;
    }

    public double getY()
    {
		// Invert the Y axis so that up on the stick is positive.
        double y = normalize(joy.getAxis(Joystick.AxisType.kY) - YCENTER, YMIN, YMAX) * -1.0;

        y = expo(y, YEXPO);

        //System.out.print("y: " + y);

        return y;
    }

    public double getX()
    {
        double x = normalize(joy.getAxis(Joystick.AxisType.kX), XMIN, XMAX);

        x = expo(x, XEXPO);

        //System.out.print(" x: " + x);

        return x;
    }

    public double getZ()
    {
        // Invert the Z axis so that up on the stick is positive
        return normalize(joy.getAxis(Joystick.AxisType.kZ), ZMIN, ZMAX) * -1.0;
    }

    public double getRot()
    {
        double rot = normalize(joy.getRawAxis(5) - ROTCENTER, ROTMIN, ROTMAX) / 2.0;

        rot = expo(rot, ROTEXPO);

        //System.out.println(" rot: " + rot);

        return rot;
    }

    public boolean getRightSw()
    {
        return joy.getButton(Joystick.ButtonType.kTop);
    }

    public boolean getLeftSw()
    {
        return joy.getButton(Joystick.ButtonType.kTrigger);
    }

    // Normalize input values to -1.0 to 1.0
    private double normalize(double joyVal, double min, double max)
    {
        
        double retVal = 0.0;

        if (joyVal < 0.0)
            retVal = Math.abs(joyVal) / min;
        else if (joyVal > 0.0)
            retVal = Math.abs(joyVal) / max;

        if (retVal < -1.0)
            retVal = -1.0;
        else if (retVal > 1.0)
            retVal = 1.0;

        return retVal;
        
        //return joyVal;
    }

    // Apply exponential function 
    private double expo(double x, double a)
    {
        return (a * (x*x*x) + (1-a) * x);
    }
}
