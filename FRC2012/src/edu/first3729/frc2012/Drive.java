package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.*;
import com.sun.squawk.util.MathUtils;

public class Drive {
	
	private Jaguar fl;
	private Jaguar fr;
	private Jaguar bl;
	private Jaguar br;
	
	private double _x_prev;
	private double _y_prev;
	private double _z_prev;
	private double fl_out, fr_out, br_out, bl_out;
	
	double ramp(double desired_output, double current_output, double increment)
	{
		if (desired_output  <= .1 && desired_output >= -.1);
		increment /= 2;
		if (desired_output < current_output)
		{
			return current_output - increment;
		}    
		else if (desired_output > current_output)
		{    
			return increment + current_output;
		}
		else
		{
			return current_output;
		}
	}
	
	public Drive()
	{
		_y_prev = _x_prev = _z_prev = 0.0;
		fl = new Jaguar(Params.fl_port);
		fr = new Jaguar(Params.fr_port);
		bl = new Jaguar(Params.bl_port);
		br = new Jaguar(Params.br_port);
		
	}
	
	public void drive_tank(double left, double right)
	{
		
	}
	
	public void drive_tank_noramp(double left, double right)
	{
		
	}
	
	// Input from x and y axes on joystick, mapped to y = speed, x = turn
	public void drive_arcade(double x, double y)
	{
		// If not pushing forward much, switch to tank mode to turn in place
		if ((y <= 0.1 && y > 0) || (y >= -0.1 && y < 0))
		{
			this.drive_tank(x * 0.75, -x * 0.75);
		}
		else
		{
			double left, right;
			// If turning left:
			if (x < 0)
			{
				double mag = MathUtils.log(-y);
				double ratio = (mag - 0.5) / (mag + 0.5);
				if (ratio == 0) ratio = .0000000001;
				left = y / ratio;
				right = y;
			}
			// If turning right:
			else if (x > 0)
			{
				double mag = MathUtils.log(y);
				double ratio = (mag - 0.5) / (mag + 0.5);
				if (ratio == 0) ratio = .0000000001;
				left = y;
				right = y / ratio;
			}
			else
			{
				left = y;
				right = y;
			}
			// Keep everything within the confines of [-1.0, 1.0]
			left = ( (left > 1.0) ? 1.0 : left);
			left = ( (left < -1.0) ? 1.0 : left);
			right = ( (right > 1.0) ? 1.0 : right);
			right = ( (right < -1.0) ? 1.0 : right);
			fl.set(left);
			fr.set(right);
			bl.set(left);
			br.set(right);
		}

	}
	
	// Cool suggestion found on CD using three degrees of freedom for turning.  Might work, might not.
	// If all else fails, we can use edu.wpi.first.wpilibj.RobotDrive.mecanumDrive_polar(double, double, double)
	public void drive_mecanum(double x, double y, double z)
	{
		x = ramp(x, _x_prev, Params.x_ramp_increment);
		y = ramp(y, _y_prev, Params.y_ramp_increment);
		z = ramp(z, _z_prev, Params.z_ramp_increment);
		
		fl_out = y + x + z;
		fr_out = y - x - z;
		bl_out = y - x + z;
		br_out = y + x - z;
		
		// Maximum absolute value of all output speeds - this next bit normalizes the outputs to no more than 1.0
		double max = Math.max(Math.max(Math.abs(fl_out), Math.abs(fr_out)), Math.max(Math.abs(br_out), Math.abs(bl_out)));
		if (max > 1.0) {
			fl_out /= max;
			fr_out /= max;
			br_out /= max;
			bl_out /= max;
		}
		
		fl.set(fl_out);
		br.set(br_out);
		fr.set(fr_out);
		bl.set(bl_out);
		
		_x_prev = x;
		_y_prev = y;
		_z_prev = z;
	}
	
}
