/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputs;

/**
 *
 * @author adam
 */
public class VarSpeedMotor extends GenericMotor
{
    double rampVal = 0.0;
    double lastOutput = 0.0;

    public VarSpeedMotor(int device, boolean reversed, double rampVal)
    {
        super(device, reversed);
        this.rampVal = rampVal;
    }

    public void set(double pwm)
    {
        /*
        double ramp = rampVal;

        if (lastOutput >= -0.2 && lastOutput <= 0.2)
            ramp = 0.01;
        
        //if (rampVal != 0.0)
        //{
            if (pwm < lastOutput)
                lastOutput -= ramp;
            else
                lastOutput += ramp;

            super.set(lastOutput);
        //}
        //else
        //    super.set(lastOutput);
        */
        super.set(pwm);
    }
}
