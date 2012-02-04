/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author matthewhaney
 */
public class Manipulator {
    
    private Relay shooter1;
    private Relay shooter2;
    private Relay elevator;
    private Relay bridge;
    private Relay intake;
    
    public Manipulator()
    {
        shooter1.setDirection(Relay.Direction.kBoth);
        shooter1.set(Relay.Value.kOff);
        shooter2.setDirection(Relay.Direction.kBoth);
        shooter2.set(Relay.Value.kOff);
        elevator.setDirection(Relay.Direction.kBoth);
        elevator.set(Relay.Value.kOff);
        bridge.setDirection(Relay.Direction.kBoth);
        bridge.set(Relay.Value.kOff);
        intake.setDirection(Relay.Direction.kBoth);
        intake.set(Relay.Value.kOff);
    }
    
    public void shoot(boolean state)
    {
        
    }
    
    public void lift(boolean state)
    {
        
    }
    
    public void intake(boolean state)
    {
        
    }
    
    
    public void bridge(boolean state)
    {
        
    }
    
}
