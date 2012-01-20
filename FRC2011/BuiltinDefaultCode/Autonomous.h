/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Autonomous.h
 \brief File containing definition of \a Autonomous class, which is used within the \a AutomonousPeriodic function in the main program to simplify things.
 \authors Matthew Haney, Drew Lazzeri
 
 */

#ifndef _RJHS_FRC_2011_AUTONOMOUS_H_
#define _RJHS_FRC_2011_AUTONOMOUS_H_

#include "macros.h"
#include "Drive.h"
#include "Manipulator.h"
#include "Controller.h"
#include <string>
#include <sstream>

namespace RJFRC2011
{
	/*!
	 \class RJFRC2011::Autonomous
	 \brief Class managing the Autonomous period of the competition
	 \details Manages a "state machine" (essentially a \a switch block) that defines which of a number of "states" to execute based on inputs from three light sensors.  These states are: check light sensors, move, correct left, correct right, place tube.
	 */
	class Autonomous
	{
	private:
		//! The starting lane of the robot; 1 for center, 0 for left or right.
		UINT8 lane;
		//! If we're in the center lane: do we go left or right at the fork?
		char forkDirection;
		
		//! Pointer to object abstracting drive mechanism
		Drive * _drive;
		//! Pointer to object abstracting manipulator
		Manipulator * _manip;
		//! Pointer to object abstracting controller
		Controller * _controller;
		//! Pointer to object representing LCD screen on the Driver Station.  Used toprint feedback to the screen.
		DriverStationLCD * _screen;
		
		// Light sensor variables
		//! Pointer to object interfacing with leftmost light sensor
		DigitalInput * lightSensorLeft;
		//! Pointer to object interfacing with center light sensor
		DigitalInput * lightSensorCenter;
		//! Pointer to object interfacing with rightmost light sensor
		DigitalInput * lightSensorRight;
		
		//! Physical switch controlling which lane our robot starts out in
		DigitalInput * autonomousLaneSwitch;
		//! Physical switch controlling which direction our robot is to take if it's in the center lane
		DigitalInput * autonomousForkSwitch;
		
		string leftOut;
		string rightOut;
		string centerOut;
		bool hasEjectedTube;
		string lastCorrectDirection;
		int lightLeft, lightCenter, lightRight;
		
		// States
		void initialState();		// state 0
		void checkForLines();		// state 1
		void move();				// state 2
		void correctRight();		// state 3
		void correctLeft();			// state 4
		void placeTube();			// state 5
		int flip(int x);
	public:
		Autonomous(Drive * drive, Manipulator * manip, Controller * controller, DriverStationLCD * screen);
		~Autonomous();
		bool DONE;
		void Go();
		void Init();
		void printLightSensors();
	};
};

#endif
