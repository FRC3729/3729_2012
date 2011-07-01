/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputs;

/**
 *
 * @author adam
 */
public class OnOffMotor extends GenericMotor
{
    private double speed = 0.0;

    public OnOffMotor(int device, boolean reversed, double speed)
    {
        super(device, reversed);

        this.speed = speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    public void setOn(boolean motorOn)
    {
        if (motorOn)
            super.set(speed);
        else
            super.set(0.0);
    }
}
