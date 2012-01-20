package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.*;

public class Input {

	private Joystick _joy_left;
	private Joystick _joy_right;
	private Joystick _controller;
	
	// Set to differentiate between drive modes.
	// m = mecanum
	// a = arcade
	// t = tank
	protected char mode = 'm';
	
	public Input()
	{
		this._joy_left = new Joystick(1);
		this._joy_left = new Joystick(2);
		this._controller = new Joystick(3);
	}
	
	public void setMode(char m)
	{
		this.mode = m;
	}
	
	public char getMode() { return mode; } 
	
	public double getX()
	{
		switch(mode)
		{
		case 'm':
			return expo(normalize(this._joy_left.getX(), -1.0, 1.0), Params.JOYEXPO);
		case 'a':
			return expo(normalize(this._controller.getX(), Params.XMIN, Params.XMAX), Params.XEXPO);
		case 't':
			return expo(normalize(this._joy_left.getY(), -1.0, 1.0), Params.JOYEXPO);
		default:
			mode = 'a';
			return getX();
		}
	}
	
	public double getY()
	{
		switch(mode)
		{
		case 'm':
			return expo(normalize(this._joy_left.getY(), -1.0, 1.0), Params.JOYEXPO);
		case 'a':
			return expo(normalize(this._controller.getY(), Params.YMIN, Params.YMAX) * -1.0, Params.YEXPO);
		case 't':
			return expo(normalize(this._joy_right.getY(), -1.0, 1.0), Params.JOYEXPO);
		default:
			mode = 'a';
			return getY();
		}
	}
	
	public double getZ()
	{
		switch(mode)
		{
		case 'm':
			return expo(normalize(this._joy_right.getX(), -1.0, 1.0), Params.JOYEXPO);
		case 'a':
			return 0;
		case 't':
			return 0;
		default:
			mode = 'm';
			return getZ();
		}
	}
	
	/**
	 * @brief Exponential function that makes the controller more sensitive toward the center and less so toward the outside
	 * 
	 * @author Adam Bryant
	 * @param x Input to be expo-ed
	 * @param a Pre-defined exponentiation factor
	 * @return The expo-ed value
	 */
	private double expo(double x, double a)
	{
		return (a * (x * x * x) + (1 - a) * x);
	}
	
	/**
	 * @brief Normalize input between -1.0 and 1.0.  Also take into account min and max values given by controller.
	 *
	 * @author Adam Bryant
	 * @param joyVal Input value to be normalized
	 * @param min Minimum controller value.  If using a joystick, supply -1.0.
	 * @param max Maximum controller value.  If using a joystick, supply 1.0.
	 * @return The normalized value
	 */
	private double normalize(double joyVal, double min, double max)
	{
		double retVal = 0.0f;
		if (joyVal < 0.0)
			retVal = Math.abs(joyVal) / min;
		else if (joyVal > 0.0)
			retVal = Math.abs(joyVal) / max;
		if (retVal < -1.0)
			retVal = -1.0f;
		else if (retVal > 1.0)
			retVal = 1.0f;
		return retVal;
	}
}
