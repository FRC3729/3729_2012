/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Controller.h
 \brief File containing definition of \a Controller class, which is used in both Autonomous and Teleoperated modes to get user input.
 \authors Matthew Haney, Drew Lazzeri
 
 */

#ifndef _RJHS_FRC_2011_CONTROLLER_H_
#define _RJHS_FRC_2011_CONTROLLER_H_

#include "bindings.h"
#include "WPILib.h"


Controller * c;
c->getManipulatorAction();

Controller::getManipulatorAction();

namespace RJFRC2011
{
	/*!
	 \class RJFRC2011::Controller
	 \brief Class abstracting the controller(s) used by our driver(s)
	 \details Get input from a pair of joysticks (one of which is physically a flight simulator controller) and return those inputs.  A class like this is useful because we can modify a few lines of code to change the inputs for functions throughout the entire code.
	 */
	class Controller
	{
	private:
		//! Object abstracting our InterLink Elite controller, which WIPLib seems to think is a joystick
		Joystick * _controller;
		//! Object abstracting our legitimate Logitech Attack3 joystick
		Joystick * _joystick;
		float abs(float initial);
		float expo(float x, float a);
		float normalize(float joyVal, float min, float max);
	public:
		Controller();
		~Controller();
		float getManipulatorElevation();
		float getDriveSpeed();
		float getDriveTurn();
		static int getManipulatorAction();
		int getMinibotSwitches();
	};
};

#endif
