/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Autonomous.cpp
 \brief File containing implementations of functions found in the \a Autonomous class (found in Autonomous.h)
 \authors Matthew Haney, Drew Lazzeri
 
 */

#include "macros.h"
#include "WPILib.h"
#include "Autonomous.h"
using namespace RJFRC2011;

/*!
 \brief Construct from the addresses of a drive system, a manipulator, a controller, and  an LCD screen.
 \param drive Pointer to a \a Drive variable that will be used in both Autonomous and Teleoperated periods.
 \param manip Pointer to a \a Manipulator variable that will be used in both Autonomous and Teleoperated periods.
 \param controller Pointer to a \a Controller variable that will be used in both Autonomous and Teleoperated periods.
 \param screen Pointer to a \a DriverStationLCD variable that will be used in Autonomous, Teleoperated, and Disabled periods to write to the screen.
 */
RJFRC2011::Autonomous::Autonomous(Drive * drive, Manipulator * manip, Controller * controller, DriverStationLCD * screen)
{		
	lightLeft = lightRight = lightCenter = 0;
	
	lastCorrectDirection = "";
	hasEjectedTube = false;
	DONE = false;
	
	// Make locally-owned pointers point to same addresses as passed pointers
	this->_drive = drive;
	this->_manip = manip;
	this->_controller = controller;
	this->_screen = screen;
	
	// Initialize sensors
	lightSensorRight = new DigitalInput(LIGHT_SENSOR_RIGHT_PORT);
	lightSensorCenter = new DigitalInput(LIGHT_SENSOR_CENTER_PORT);
	lightSensorLeft = new DigitalInput(LIGHT_SENSOR_LEFT_PORT);
	
	// Initialize physical switches
	autonomousLaneSwitch = new DigitalInput(AUTONOMOUS_LANE_SWITCH_PORT);
	autonomousForkSwitch = new DigitalInput(AUTONOMOUS_FORK_SWITCH_PORT);
}

//! Destructor.  Kill stuff.
RJFRC2011::Autonomous::~Autonomous()
{
	delete autonomousForkSwitch;
	delete autonomousLaneSwitch;
	delete lightSensorLeft;
	delete lightSensorRight;
	delete lightSensorCenter;
}

/*!
 \brief The initial state of the robot.
 \details Read input from the switches and set variables accordingly.  If lane switch is off, we're in a side lane; if it's on, we're in the center one.  If we're in the center, read the second fork switch: if it's on, go left; if not, go right.
 */
void RJFRC2011::Autonomous::initialState()			// state 0
{
	lightLeft = lightRight = lightCenter = 0;	
	lastCorrectDirection = "";
	hasEjectedTube = false;
	DONE = false;
	this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "initialState");
	// Input is pulled low, so this should be the default.
	if (autonomousLaneSwitch->Get() == 0)			// Left or right lane
	{
		lane = 0;
	}
	else if (autonomousLaneSwitch->Get() == 1)		// Center lane
	{
		lane = 1;
		if (autonomousForkSwitch->Get() == 1)		// Left @ fork
		{
			forkDirection = 'l';
		}
		// Input is pulled low, so this should be the default.
		else if (autonomousForkSwitch->Get() == 0)	//Right @ fork
		{
			forkDirection = 'r';
		}
	}
	lightLeft = lightRight = lightCenter = 0;
}

/*!
 \brief
  The state in which the robot is looking for tape lines on the floor
 \details Read input from the 3 light sensors and store the results in three variables.
 */
void RJFRC2011::Autonomous::checkForLines()		// state 1
{
	this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "checkForLines");
	lightLeft = lightCenter = lightRight = 0;
	// First bit of lightVals is value of left light
	lightLeft = lightSensorLeft->Get() ? 1 : 0;
	// Second bit is value of center light
	lightCenter = lightSensorCenter->Get() ? 1 : 0;
	// Third is value of right light
	lightRight = lightSensorRight->Get() ? 1 : 0; 
}

/*!
 \brief State in which the robot moves forward 
 \details Print to the screen that we're in \a move() and drive forward at \a AUTO_DRIVE_SPEED
 */
void RJFRC2011::Autonomous::move()					// state 2
{
	this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "move");
	this->_drive->drive_noramp(AUTO_DRIVE_SPEED, 0);
}

/*!
 \brief State in which the robot moves slightly to the right
 \details Print to the screen that we're in \a correctRight() and turn right by driving the left wheels at \a AUTO_TURN_SPEED and the right wheels at -.15.
 */
void RJFRC2011::Autonomous::correctRight()			// state 3
{
	this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "correctRight");
	this->_drive->tank_drive(AUTO_TURN_SPEED, -.15);
	lastCorrectDirection = "r";
}

/*!
 \brief State in which the robot moves slightly to the left.
 \details Print to the screen that we're in \a correctLeft() and turn left by driving the left wheels at -.15 and the right wheels at \a AUTO_TURN_SPEED.
 */
void RJFRC2011::Autonomous::correctLeft()			// state 4
{
	this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "correctLeft");
	this->_drive->tank_drive(-.15, AUTO_TURN_SPEED);
	lastCorrectDirection = "l";
}

/*!
 \brief State in which the robot ejects the tube (ideally onto a peg)
 \details Brake the robot to a stop, print that we're in \a placeTube(), and run the ejection wheels for one second.
 */
void RJFRC2011::Autonomous::placeTube()				// state 5
{
	if (!hasEjectedTube)
	{
		this->_drive->drive_noramp(0, 0);
		this->_drive->drive_noramp(AUTO_BRAKE_SPEED, 0);
		this->_screen->PrintfLine(DriverStationLCD::kUser_Line1, "placeTube");
		this->_screen->UpdateLCD();
		this->_drive->drive(0, 0);
		this->_manip->ejectTube();
		Wait(3);
		this->_manip->stopManipulatorAction();
		this->_manip->stopManipulatorElevation();
	}
	hasEjectedTube = true;
}

/*!
 \brief State machine function that decides which state to go into
 \details Operates on the \a lightX variables to tell where the line is, with 0 indicating that the sensor saw nothing and 1 indicating that the sensor detected something (ideally the tape).  We then use a sereis of \a if statements to change to the appropriate state.
 */
void RJFRC2011::Autonomous::Go()
{
	checkForLines();																	// L, C, R
	
	if ((lightLeft == 0) && (lightRight == 0) && (lightCenter == 0))					// 000
	{
		// Can't see the line
		if (lastCorrectDirection == "r")
			correctLeft();
		else if (lastCorrectDirection == "l")
			correctRight();
		else
			move();
	}
	else if ((lightLeft == 0) && (lightRight == 1) && (lightCenter == 0))				// 001
	{
		// Way far left
		correctRight();
	}
	else if ((lightLeft == 0) && (lightRight == 0) && (lightCenter == 1))				// 010
	{
		// Ideal; right on line
		move();
	}
	else if ((lightLeft == 0) && (lightRight == 1) && (lightCenter == 1))				// 011
	{
		// a little left
		correctRight();
	}
	else if ((lightLeft == 1) && (lightRight == 0) && (lightCenter == 1))				// 110
	{
		// A little to the right
		correctLeft();
	}
	else if ((lightLeft == 1) && (lightRight == 0) && (lightCenter == 0))				// 100
	{
		// Way far right
		correctLeft();
	}
	else if ((lightLeft == 1) && (lightRight == 1) && (lightCenter == 0))				// 101
	{
		// Pick a direction! We're at the fork
		if (forkDirection == 'l')
		{
			correctLeft();
		}
		else
		{
			correctRight();
		}
	}
	else if ((lightLeft == 1) && (lightRight == 1) && (lightCenter == 1))				// 111
	{
		// We're done; we've reached the 'T'
		if (!DONE)
		{
			placeTube();
		}
		DONE = true;
	}
	else																				// ???
	{
		// Reset and try again
		lightCenter = lightLeft = lightRight = 0;
	}
}

//! Convenience method.  Same as \a initialState()
void RJFRC2011::Autonomous::Init()
{
	this->initialState();
}
//! Debug info.  Prints the values of lightLeft, lightCenter, and lightRight on the screen.
void RJFRC2011::Autonomous::printLightSensors()
{
	//checkForLines();
	_screen->PrintfLine(DriverStationLCD::kUser_Line3, topchar(lightLeft));
	_screen->PrintfLine(DriverStationLCD::kUser_Line4, topchar(lightCenter));
	_screen->PrintfLine(DriverStationLCD::kUser_Line5, topchar(lightRight));
	_screen->UpdateLCD();
}

/*!
 \brief If x is 0, return 1! If x is 1, return 0!
 \param x The value to be flipped
 \return The flipped value
 */
int RJFRC2011::Autonomous::flip(int x)
{
	if (x == 0)
		return 1;
	else if (x == 1)
		return 0;
	else
		return x;
}
