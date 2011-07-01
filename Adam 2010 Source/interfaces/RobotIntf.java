/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author adam
 */
public interface RobotIntf
{
    public void init();

    // Call this to update sub-systems, like the kicker
    public void update();

    // Drive functions
    public void driveMove( double fwd, double slide, double rot );
    public void driveStop();

    // Dribbler functions
    public void dribblerSpeed(double speed);
    public boolean dribblerLimitOn();

    // Kicker functions
    public void kickerArm();
    public void kickerKick();
    public boolean kickerLimitOn();

    // Gyro
    public Gyro getGyro();
}
