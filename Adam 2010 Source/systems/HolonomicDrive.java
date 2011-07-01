/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author adam
 */
public class HolonomicDrive
{
    private SpeedController lf, lr, rf, rr;

    public HolonomicDrive(SpeedController lf,
                          SpeedController lr,
                          SpeedController rf,
                          SpeedController rr)
    {
        this.lf = lf;
        this.lr = lr;
        this.rf = rf;
        this.rr = rr;
    }

    public void drive( double fwd, double slide, double rot )
    {
        // Do the holonomic drive voodoo here
        double leftF = fwd + slide + rot;
        double leftR = fwd - slide + rot;
        double rightF = fwd - slide - rot;
        double rightR = fwd + slide - rot;

        // Find max value
        double max = Math.abs(leftF);

        if (Math.abs(leftR) > max)
            max = Math.abs(leftR);

        if (Math.abs(rightF) > max)
            max = Math.abs(rightF);

        if (Math.abs(rightR) > max)
            max = Math.abs(rightR);

        // If > 1.0, then normalize all values
        if (max > 1.0)
        {
            leftF /= max;
            leftR /= max;
            rightF /= max;
            rightR /= max;
        }

        lf.set(leftF);
        lr.set(leftR);
        rf.set(rightF);
        rr.set(rightR);
    }

    public void stop()
    {
        lf.set(0.0);
        lr.set(0.0);
        rf.set(0.0);
        rr.set(0.0);
    }
}
