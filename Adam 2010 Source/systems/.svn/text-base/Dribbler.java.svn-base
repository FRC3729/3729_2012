/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import outputs.VarSpeedMotor;

/**
 *
 * @author adam
 */
public class Dribbler
{
    private VarSpeedMotor lm, rm;

    public Dribbler(VarSpeedMotor lm, VarSpeedMotor rm)
    {
        this.lm = lm;
        this.rm = rm;
    }

    // The value coming in will be directly off of the joystick, so it will
    // range from -1.0 to 1.0.  We need to translate to a value from 0 to 1.0
    // and it would probably be a good idea to NOT turn on the motor until
    // the stick is over 10% of the way up.
    public void setSpeed(double speed)
    {
        double output = 1.0 - ((1.0 - speed) / 2.0);
        
        System.out.println("output: " + output);
        
        lm.set( output );
        rm.set( output );
    }
}
