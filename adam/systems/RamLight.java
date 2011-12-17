/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author adam
 */
public class RamLight
{
    private Relay ram;
    private AnalogChannel limit;

    public RamLight(int port, AnalogChannel limit)
    {
        ram = new Relay(port);
        this.limit = limit;
    }

    public void update()
    {
        if (limit.getVoltage() < 2.0)
            this.On();
        else
            this.Off();
    }

    public void On()
    {
        ram.set(Relay.Value.kForward);
    }

    public void Off()
    {
        ram.set(Relay.Value.kOff);
    }
}
