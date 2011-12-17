/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Watchdog;
import outputs.OnOffMotor;

/**
 *
 * @author adam
 */
public class Kicker
{

    private OnOffMotor mot;
    private int state = 0;
    private AnalogChannel limit;
    //private boolean active = false;
    private KickThread kt = null;

    public Kicker(OnOffMotor mot, AnalogChannel limit)
    {
        this.mot = mot;
        this.limit = limit;
    }

    public void armIt()
    {
        kt = new KickThread(mot,limit);
        kt.start();
    }

    public void kickIt()
    {
        kt = new KickThread(mot,limit);
        kt.start();
    }

/*
    public void kickIt()
    {
        state = 3;

//        while (state != 0)
//        {
//            Watchdog.getInstance().feed();
//
//            update();
//        }
    }

    public void armIt()
    {
        state = 1;

//        while (state != 0)
//        {
//            Watchdog.getInstance().feed();
//
//            update();
//        }
    }

    public void update()
    {
        //System.out.println("Kicker update(), state: " + state);

        //System.out.println("Limit: " + limit.getVoltage());
        //System.out.println("state: " + state);
        switch (state)
        {
            case 0:
                // Do nothing
                System.out.println("kicker 0");
                break;
            case 1:
                // Run motor until limit switch activated
                //System.out.println("kicker 1");
                System.out.println("kicker 1, limit: " + limit.getVoltage());
                if (limit.getVoltage() > 2.0)
                {
                    mot.setOn(true);
                }
                state++;
                break;
            case 2:
                // Wait until limit switch closed
                //System.out.println("kicker 2");
                System.out.println("kicker 2, limit: " + limit.getVoltage());
                if (limit.getVoltage() <= 2.0)
                {
                    //System.out.println("kicker 2, Switch closed");
                    mot.setOn(false);
                    state = 0;
                }
                break;
            case 3:
                // Run motor until limit switch released
                System.out.println("kicker 3");
                mot.setOn(true);
                state++;
                break;
            case 4:
                // Wait until limit switch opened
                System.out.println("kicker 4");
                if (limit.getVoltage() > 2.0)
                {
                    state = 2;
                }
                break;
        }
    }
 */
}
