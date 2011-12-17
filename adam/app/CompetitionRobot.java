/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import interfaces.RobotIntf;
import outputs.*;
import systems.*;

/**
 *
 * @author adam
 */
public class CompetitionRobot implements RobotIntf
{
    private VarSpeedMotor lf;
    private VarSpeedMotor lr;
    private VarSpeedMotor rf;
    private VarSpeedMotor rr;

    private VarSpeedMotor leftDribbler;
    private VarSpeedMotor rightDribbler;
    private OnOffMotor kickMotor;

    private AnalogChannel shLimit;
    private AnalogChannel drLimit;

    public Gyro gyro;
    
    // Systems
    private Dribbler dribbler;
    private Kicker kicker;
    private HolonomicDrive dt;
    private RamLight ramLight;
    private AxisCamera cam;

    public void init()
    {
        lf = new VarSpeedMotor(10, false, 0.04);
        lr = new VarSpeedMotor(12, false, 0.04);
        rf = new VarSpeedMotor(11, true, 0.04);
        rr = new VarSpeedMotor(13, true, 0.04);
        leftDribbler = new VarSpeedMotor(20, true, 0.04);
        rightDribbler = new VarSpeedMotor(1, false, 0.04);
        kickMotor = new OnOffMotor(26, true, 0.85);

        shLimit = new AnalogChannel(7);
        drLimit = new AnalogChannel(6);

        gyro = new Gyro(1);
        gyro.setSensitivity(.007);

        // Systems
        dribbler = new Dribbler(leftDribbler, rightDribbler);
        kicker = new Kicker(kickMotor, shLimit);
        dt = new HolonomicDrive(lf, lr, rf, rr);
        ramLight = new RamLight(1, drLimit);

        ramLight.Off();

        // Setup camera
        cam = AxisCamera.getInstance();
        cam.writeResolution(AxisCamera.ResolutionT.k320x240);
        cam.writeBrightness(0);
    }

    public void update()
    {
        //kicker.update();
        ramLight.update();
    }

    public void driveMove(double fwd, double slide, double rot)
    {
        dt.drive(fwd, slide, rot);
    }

    public void driveStop()
    {
        dt.stop();
    }

    public void dribblerSpeed(double speed)
    {
        dribbler.setSpeed(speed);
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
        if (shLimit.getVoltage() < 2.0)
            return true;
        else
            return false;
    }

    public Gyro getGyro()
    {
        return gyro;
    }

    public boolean dribblerLimitOn()
    {
        if (this.drLimit.getVoltage() < 2.0)
            return true;
        else
            return false;
    }
}
