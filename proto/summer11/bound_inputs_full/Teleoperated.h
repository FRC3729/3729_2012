/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Teleoperated.h
 \brief File containing definition of \a Teleoperated class, which is used in the main program during Teleoperated mode to simplify things.
 \authors Matthew Haney, Drew Lazzeri
 */

#ifndef _RJHS_FRC_2011_TELEOPERATED_H_
#define _RJHS_FRC_2011_TELEOPERATED_H_

#include "bindings.h"
#include "WPILib.h"
#include "Drive.h"
#include "Manipulator.h"
#include "Controller.h"
#include <sstream>

namespace RJFRC2011
{
	/*!
	 \class Teleoperated
	 \brief Class managing robot performance in Teleoperated mode
	 \details Gets user input from two controllers (abstracted as instances of class \a Joystick), processes said inputs (normalizing joystick values, dead space, etc.), and feeds them to a series of outputs (mainly the \a Drive class).
	 */
	class Teleoperated
	{
	private:
		//! Pointer to object abstracting drive system
		Drive * _drive;
		//! Pointer to object abstracting manipulator
		Manipulator * _manip;
		//! Pointer to object abstracting controller
		Controller * _controller;
		//! The screen of the driver station
		DriverStationLCD * _screen;
		//! The minibot shelf relay
		Relay * minibotShelf;
		//! Top manipulator limit switch
		DigitalInput * top;
		//! Bottom manipulator limit switch
		DigitalInput * bottom;
		//! User input: requested manipulator elevation
		float manipulatorElevation;
		//! User input: requested drive speed
		float driveSpeed;
		//! User input: requested drive turn
		float driveTurn;
		//! User input: requested manipulator action
		int manipulatorAction;
		//! User input: requested minibot switches
		int minibotSwitches;
		//! Used internally (in \a genericCondition()) to manage state switching
		int _nextState;
		//! Used to flag that we want to rotate the tube down (after inputting it)
		bool rotateTubeDownFlag;
	public:
		Teleoperated(Drive * drive, Manipulator * manip, Controller * controller, DriverStationLCD * screen);
		~Teleoperated();
		void Init();
		void Go();
		void testLimitSwitches();
	};	
};

#endif
