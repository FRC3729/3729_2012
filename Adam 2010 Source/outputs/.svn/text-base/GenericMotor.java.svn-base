/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputs;

import utilities.CANJaguar;

/**
 *
 * @author adam
 */
public class GenericMotor extends CANJaguar
{
    private boolean reversed = false;
    
    /**
     * Constructor
     * @param device - CAN network ID
     *
     * Assumes that this motor is NOT reversed.
     */
    public GenericMotor(int device)
    {
        super(device);
    }

    /**
     * Constructor
     * @param device - CAN network ID
     * @param reversed - Motor outputs are reversed if true
     */
    public GenericMotor(int device, boolean reversed )
    {
        super(device);
        this.reversed = reversed;
    }

    /**
     *
     * @param pwm - PWM value to output
     *
     * If this motor is constructed as reversed, then the PWM value is
     * multiplied by -1.0 before being output.
     */
    public void set(double pwm)
    {
        if (reversed)
            pwm *= -1.0;

        super.set(pwm);
    }
}
