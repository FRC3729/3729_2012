/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Controller.cpp
 \brief File containing implementations of functions found in the \a Controller class (found in Controller.h)
 \authors Matthew Haney, Drew Lazzeri
 
 */

#include "Controller.h"
#include "WPILib.h"
#include "macros.h"

using namespace RJFRC2011;

/*!
 \brief Constructor.
 \details Basically, initialize the controllers to USB ports 1 and 2.
 */
RJFRC2011::Controller::Controller()
{
	this->_controller = new Joystick(1);
	this->_joystick = new Joystick(2);
}

/*!
 \brief Destructor.  Kill stuff.
 */
RJFRC2011::Controller::~Controller()
{
	delete _controller;
	delete _joystick;
}

/*!
 \brief Get user-requested manipulator elevation.
 \details Reads input from the y-axis on the joystick.
 \return The received value (inverted).
 */
float RJFRC2011::Controller::getManipulatorElevation()
{
	//The below was used when we still had this input on the left stick of the InterLink Elite
	//return normalize(this->_controller->GetZ(), ZMIN, ZMAX) * -1.0;
	return this->_joystick->GetY() * -1.0;
}

/*!
 \brief Get user-requested drive speed.
 \details Reads input from the y-axis on the right stick, normalizes it to a value between -1.0 and 1.0, and then exponentiates it (so the stick's less sensitive in the center and more sensitive at the edges).
 \return The received, normalized, and expo-ed value (inverted [if the inversion switch {button #2} is thrown]).
 */
float RJFRC2011::Controller::getDriveSpeed()
{
	// Y-axis right; speed
	if (this->_controller->GetRawButton(2) == 1)
		return expo(normalize(this->_controller->GetY(), YMIN, YMAX) * -1.0, YEXPO) * -1.0;
	else
		return expo(normalize(this->_controller->GetY(), YMIN, YMAX) * -1.0, YEXPO);
}

/*!
 \brief Get user-requested drive turn speed.
 \details Reads input from the x-axis on the right stick, normalizes it to a value between -1.0 and 1.0, and then exponentiates it (so the stick's less sensitive in the center and more sensitive at the edges).
 \return The received, normalized, and expo-ed value(inverted [if the inversion switch {button #2} is thrown]).
 */
float RJFRC2011::Controller::getDriveTurn()
{
	// X-axis right; turn
	if (this->_controller->GetRawButton(2) == 1)
		return expo(normalize(this->_controller->GetX(), XMIN, XMAX), XEXPO) * -1.0;
	else
		return expo(normalize(this->_controller->GetX(), XMIN, XMAX), XEXPO);
}

/*!
 \brief Get user-requested manipulator action (input, rotate, or eject).
 \details Read input from three buttons on the joystick: the trigger (1) and two thumb buttons (2 and 3).  If any one of them is pressed, return that button's ID.  If multiple are pressed, or none are pressed, return 0.
 \return The ID of the received button.
 */
int RJFRC2011::Controller::getManipulatorAction()
{
	// X-axis left; manipulator receive/eject controls
	//return normalize(this->_controller->GetRawAxis(5), ROTMIN, ROTMAX);
	// 0 = none
	// 1 (trigger) = eject
	// 2 = rotate down
	// 3 = suck in
	if ((this->_joystick->GetRawButton(3) == 1 && this->_joystick->GetRawButton(1) == 1) || (this->_joystick->GetRawButton(3) == 1 && this->_joystick->GetRawButton(2) == 1) || (this->_joystick->GetRawButton(2) == 1 && this->_joystick->GetRawButton(1) == 1))
		return 0;
	else if (this->_joystick->GetRawButton(3) == 1)
		return 3;
	else if (this->_joystick->GetRawButton(2) == 1)
		return 2;
	else if (this->_joystick->GetRawButton(1) == 1)
		return 1;
	else
		return 0;
}

/*!
 \brief Get user-requested minibot shelf action (in/out).
 \details Read input from two thumb buttons on the joystick (buttons 4 and 5) and return ther states in a single variable.
 \return An integer with the last two bits being the right and left button inputs, respectively.
 */
int RJFRC2011::Controller::getMinibotSwitches()
{
	int switches = 0;
	// First bit is right switch value
	switches |= (this->_joystick->GetRawButton(4) << 0);
	// Second bit is left switch value
	switches |= (this->_joystick->GetRawButton(5) << 1);
	return switches;
}

/*!
 \brief A function that "normalizes" inputs from the joysticks (because they don't give perfect -1.0 to 1.0 values).
 \details If the requested value is negative, return its percentage of the minimum possible value; if it's possible, do the same with the max.  If it's zero, of course, return zero.
 \param joyVal the input from the joystick
 \param min the minimun joystick value
 \param max the maximum joystick value
 \return the normalized value
 \author Adam Bryant
 */
float RJFRC2011::Controller::normalize(float joyVal, float min, float max)
{
	float retVal = 0.0;
	if (joyVal < 0.0)
		retVal = abs(joyVal) / min;
	else if (joyVal > 0.0)
		retVal = abs(joyVal) / max;
	if (retVal < -1.0)
		retVal = -1.0;
	else if (retVal > 1.0)
		retVal = 1.0;
	return retVal;
}

/*!
 \brief An exponential function used to make joysticks less sensitive near the center and more sensitive towards the edges.
 \details Basically, plug the value requested by the user and a predefined constant into an exponential equation and return the result.
 \param x the value to be exponentiated
 \param a a predefined exponential factor
 \return the "expo-ed" value
 \author Adam Bryant
 */
float RJFRC2011::Controller::expo(float x, float a)
{
	return (a * (x * x * x) + (1 - a) * x);
}

/*!
 \brief Absolute value of a float, since I'm not sure if we can import the <cmath> library onto the cRIO.  EDIT: turns out we can, but I'll leave this here to conserve memory.
 \param initial the initial value
 \return the absolute value of the passed value; if it's negative, make it positive
 \author Matthew Haney
 */
float RJFRC2011::Controller::abs(float initial)
{
	if (initial < 0)
		return (initial * -1.0);
	else if (initial > 0)
		return initial;
	else 
		return 0.0;
}
