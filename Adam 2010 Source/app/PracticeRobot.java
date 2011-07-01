/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import edu.wpi.first.wpilibj.*;
import interfaces.RobotIntf;
import outputs.*;
import systems.*;

/**
 *
 * @author adam
 */
public class PracticeRobot implements RobotIntf
{
    // Motors
    private VarSpeedMotor lf;
    private VarSpeedMotor lr;
    private VarSpeedMotor rf;
    private VarSpeedMotor rr;

    private VarSpeedMotor leftDribbler;
    private VarSpeedMotor rightDribbler;
    private OnOffMotor kickMotor;

    // Sensors
    private Gyro gyro;

    private AnalogChannel shLimit;
    private AnalogChannel drLimit;

    // Systems
    private HolonomicDrive dt;
    private Dribbler dribbler;
    private Kicker kicker;

    public void init()
    {
        gyro = new Gyro(1);
        gyro.setSensitivity(.007);

        shLimit = new AnalogChannel(7);
        drLimit = new AnalogChannel(6);

        /*
        lf = new VarSpeedMotor(10, false, 0.04);
        lr = new VarSpeedMotor(12, false, 0.04);
        rf = new VarSpeedMotor(11, true, 0.04);
        rr = new VarSpeedMotor(13, true, 0.04);
        */
        kickMotor = new OnOffMotor(21, true, 0.75);

        
        //leftDribbler = new VarSpeedMotor(14, true, 0.04);
        //rightDribbler = new VarSpeedMotor(21, false, 0.04);
        //dt = new HolonomicDrive(lf, lr, rf, rr);

        // Systems
        //dribbler = new Dribbler(leftDribbler, rightDribbler);
        kicker = new Kicker(kickMotor, shLimit);
    }

    public void update()
    {
        //System.out.println("PracticeRobot update()");
        
        //kicker.update();
    }

    public void driveMove(double fwd, double slide, double rot)
    {
        //dt.drive(fwd, slide, rot);
    }

    public void driveStop()
    {
        //dt.stop();
    }

    public void dribblerSpeed(double speed)
    {
        //dribbler.setSpeed(speed);
    }

    public boolean dribblerLimitOn()
    {
        if (drLimit.getVoltage() < 2.0)
            return true;
        else
            return false;
    }

    public void kickerArm()
    {
        kicker.armIt();
    }

    public void kickerKick()
    {
        kicker.kickIt();
    }

    public boolean kickerLimitOn()
    {
        System.out.println("Ana in: " + shLimit.getVoltage());

        if (shLimit.getVoltage() < 2.0)
            return true;
        else
            return false;
    }

    public void ramOn()
    {
    }

    public void ramOff()
    {
    }

    public Gyro getGyro()
    {
        return gyro;
    }
}
