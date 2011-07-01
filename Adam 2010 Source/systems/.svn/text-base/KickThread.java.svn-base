/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package systems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Timer;
import outputs.OnOffMotor;

/**
 *
 * @author adam
 */
public class KickThread extends Thread
{
    private AnalogChannel limit;
    private OnOffMotor mot;
    private Timer timer = null;

    public KickThread(OnOffMotor mot, AnalogChannel limit)
    {
        this.mot = mot;
        this.limit = limit;
    }

    public void run()
    {
        int count = 0;
        timer = new Timer();
        double time = 0;

        //boolean done = false;
        //while (!done)
        {
            // Wait until switch released
            mot.setOn(true);

            timer.start();
            while (limit.getVoltage() < 2.0 && time < 0.2)
            {
                System.out.println("Kicker 0");
                time = timer.get();
                Thread.yield();
            }

            
            // Turn off motor to let the switch stop bouncing
            mot.setOn(false);
            timer = new Timer();
            timer.start();
            time = 0;
            while (time < 0.2)
            {
                time = timer.get();
                //System.out.println("Kicker 1: time: " + time);
                Thread.yield();
            }
            
            
            // Wait until switch closed
            mot.setOn(true);
            count = 0;
            timer = new Timer();
            timer.start();
            time = 0;
            while (time < 2.0 && count < 11)
            {
                System.out.println("Kicker 2, count: " + count);
                if ( limit.getVoltage() < 2.0)
                    count++;
                
                time = timer.get();
                Thread.yield();
            }

            mot.setOn(false);
        }
    }

}
