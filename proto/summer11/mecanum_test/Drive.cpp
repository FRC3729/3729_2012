/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Drive.cpp
 \brief File containing definitions of functions declared in class \a Drive (declared in Drive.h).
 */

#include "Drive.h"
#include "WPILib.h"
using namespace RJFRC2011;

/*!
 \brief Constructor.
 \details Initialize the drive system to work with Jaguars on ports \a DRIVE_FRONT_LEFT_JAGUAR_PORT , \a DRIVE_FRONT_RIGHT_JAGUAR_PORT , \a DRIVE_BACK_LEFT_JAGUAR_PORT , and \a DRIVE_BACK_RIGHT_JAGUAR_PORT .
 */
RJFRC2011::Drive::Drive()
{
	// Create a robot using standard left/right robot drive on PWMS 1, 2, 3, and #4
	// this->_drive = new RobotDrive(DRIVE_FRONT_LEFT_JAGUAR_PORT, DRIVE_FRONT_RIGHT_JAGUAR_PORT, DRIVE_BACK_LEFT_JAGUAR_PORT, DRIVE_BACK_RIGHT_JAGUAR_PORT);
	this->fl = new Jaguar(DRIVE_FRONT_LEFT_JAGUAR_PORT);
	this->fr = new Jaguar(DRIVE_FRONT_RIGHT_JAGUAR_PORT);
	this->bl = new Jaguar(DRIVE_BACK_LEFT_JAGUAR_PORT);
	this->br = new Jaguar(DRIVE_BACK_RIGHT_JAGUAR_PORT);
	this->_speed_prev = this->_turn_prev = this->_x_prev = this->_y_prev = 0;
}

RJFRC2011::Drive::~Drive()
{
	//delete this->_drive;
	delete this->fl;
	delete this->fr;
	delete this->br;
	delete this->bl;
}

/*!
 \brief Drive the robot, ramping as you go.
 \param speed The user-requested speed
 \param turn The user-requested turn
 \details Drive the robot with the \a RobotDrive.Drive(speed, turn) function UNLESS the user wants to do a dead turn (by pushing a stick directly to the left or right), in which case we use the \a RobotDrive.TankDrive(left, right) function.
 */
void RJFRC2011::Drive::drive(float speed, float turn)
{
	// ramp speed and turn
	turn = ramp(turn, _turn_prev);
	speed = ramp(speed, _speed_prev);
	
	if ((speed <= 0.1 && speed > 0) || (speed >= -0.1 && speed < 0))
	{
		this->tank_drive(turn * 0.75, -turn * 0.75);
	}
	else
	{
		this->drive_noramp(speed, turn);
	}
	
	_speed_prev = speed;
	_turn_prev = turn;
}

/*!
 \brief Drive without ramping.  Used ONLY internally and in Autonomous, since we don't trust our drivers ;)
 \param speed The requested speed
 \param turn The requested turn
 \details Drive the robot by setting Jaguar speed controllers appropriately UNLESS we want to do a dead turn, in which case we use the \a tank_drive(left, right) function.  Based off the WPILib RobotDrive::Drive function, but with ramping tweaks.
 \authors Matthew Haney, WPILib
 */
void RJFRC2011::Drive::drive_noramp(float speed, float turn)
{
	if ((speed <= 0.1 && speed > 0) || (speed >= -0.1 && speed < 0))
	{
		this->tank_drive(turn * 0.75, -turn * 0.75);
	}
	else
	{
		float left, right;
		if (turn < 0)
		{
			float mag = log(-turn);
			float ratio = (mag - 0.5) / (mag + 0.5);
			if (ratio == 0) ratio = .0000000001;
			left = speed / ratio;
			right = speed;
		}
		else if (turn > 0)
		{
			float mag = log(turn);
			float ratio = (mag - 0.5) / (mag + 0.5);
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
		fl->Set(left);
		fr->Set(right);
		bl->Set(left);
		br->Set(right);
	}
}

/*!
 \brief Drive robot from values given for the speed of each wheel; used in autonomous.  No ramping here; however, since it won't be sporadic, we don't need it.
 \param left Speed of the left motor
 \param right Speed of the right motor
 */
void RJFRC2011::Drive::tank_drive(float left, float right)
{
	fl->Set(left);
	bl->Set(left);
	br->Set(right);
	fr->Set(right);
}

/*!
 \brief A function that "ramps" input from a joystick to motors so that an overzealous driver doesn't tear up the chassis.
 \details Increase or decrease the sent value gradually based on operator response.  With values close to zero, go even more gradually than normal.
 \param desired_output The output that the operator is trying to send
 \param current_output The current output
 \param increment The amount by which to increment ramping.  Defaults to .005
 \return the ramped value
 \author Drew Lazzeri
 */
float RJFRC2011::Drive::ramp(float desired_output, float current_output)
{
	float increment = .1;
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

/*!
 \brief GEHERGELZ0R! Let's see if this works.
 */

float RJFRC2011::Drive::drive_mecanum(float x, float y)
{
	x = ramp(x, _x_prev);
	y = ramp(y, _y_prev);
	
	fl->Set(x);
	br->Set(x);
	fr->Set(y);
	bl->Set(y);
	
	_x_prev = x;
	_y_prev = y;
}
