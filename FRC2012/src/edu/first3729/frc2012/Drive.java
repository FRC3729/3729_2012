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
            left = ramp(left, _x_prev, Params.x_ramp_increment);
            right = ramp(right, _y_prev, Params.y_ramp_increment);
            
            fl.set(left);
            bl.set(left);
            fr.set(right);
            br.set(right);
            
            _x_prev = left;
            _y_prev = right;
	}
	
	public void drive_tank_noramp(double left, double right)
	{
            fl.set(left);
            bl.set(left);
            fr.set(right);
            br.set(right);
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
	
	// Mecanum drive!
	public void drive_mecanum(double x, double y, double z)
	{
		x = ramp(x, _x_prev, Params.x_ramp_increment);
		y = ramp(y, _y_prev, Params.y_ramp_increment);
		z = ramp(z, _z_prev, Params.z_ramp_increment);
		
                // If not turning, our job is easy
                if (z == 0) {
                    fl_out = y - x;
                    fr_out = y + x;
                    bl_out = y + x;
                    br_out = y - x;
                }
		
                // Normalize between 1 and -1
                fl_out = ( (fl_out > 1.0) ? 1.0 : fl_out);
                fl_out = ( (fl_out < -1.0) ? 1.0 : fl_out);
		fr_out = ( (fr_out > 1.0) ? 1.0 : fr_out);
		fr_out = ( (fr_out < -1.0) ? 1.0 : fr_out);
                bl_out = ( (bl_out > 1.0) ? 1.0 : bl_out);
                bl_out = ( (bl_out < -1.0) ? 1.0 : bl_out);
		br_out = ( (br_out > 1.0) ? 1.0 : br_out);
		br_out = ( (br_out < -1.0) ? 1.0 : br_out);
                
		fl.set(fl_out);
		br.set(br_out);
		fr.set(fr_out);
		bl.set(bl_out);
		
		_x_prev = x;
		_y_prev = y;
		_z_prev = z;
	}
	void lock_motors()
        {
            fl.set(0);
            br.set(0);
            fr.set(0);
            bl.set(0);
        }
}
