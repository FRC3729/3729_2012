package org.usfirst.frc3729;

import edu.wpi.first.wpilibj.*;
import java.lang.Math;

public class Drive {
	
	private Jaguar fl;
	private Jaguar fr;
	private Jaguar bl;
	private Jaguar br;
	
	private double _x_prev;
	private double _y_prev;
	
	double ramp(double desired_output, double current_output) {
		double increment = .1;
		if (desired_output  <= .1 && desired_output >= -.1);
		increment = .05;
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
	
	public Drive() {
		fl = new Jaguar(Params.fl_port);
		fr = new Jaguar(Params.fr_port);
		bl = new Jaguar(Params.bl_port);
		br = new Jaguar(Params.br_port);
		
	}
	
	public void drive_tank(double left, double right) {
		
	}
	
	public void drive_tank_noramp(double left, double right) {
		
	}
	
	public void drive_arcade(double speed, double turn) {
		if ((speed <= 0.1 && speed > 0) || (speed >= -0.1 && speed < 0))
		{
			this.drive_tank(turn * 0.75, -turn * 0.75);
		}
		else
		{
			double left, right;
			if (turn < 0)
			{
				double mag = Math.log10(-turn);
				double ratio = (mag - 0.5) / (mag + 0.5);
				if (ratio == 0) ratio = .0000000001;
				left = speed / ratio;
				right = speed;
			}
			else if (turn > 0)
			{
				double mag = Math.log10(turn);
				double ratio = (mag - 0.5) / (mag + 0.5);
				if (ratio == 0) ratio = .0000000001;
				left = speed;
				right = speed / ratio;
			}
			else
			{
				left = speed;
				right = speed;
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
	
	// This may be all we need to do to make it work.  We may need to eliminate or redo ramping.  We'll have to see.
	public void drive_mecanum(double x, double y) {
		x = ramp(x, _x_prev);
		y = ramp(y, _y_prev);
		
		fl.set(x);
		br.set(x);
		fr.set(y);
		bl.set(y);
		
		_x_prev = x;
		_y_prev = y;
	}
	
}
