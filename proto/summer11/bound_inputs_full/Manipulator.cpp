/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Manipulator.cpp
 \brief File containing implementations of functions found in the \a Manipulator class (found in Manipulator.h).
 \authors Matthew Haney, Drew Lazzeri
 
 */

#include "Manipulator.h"
#include "WPILib.h"
#include "macros.h"
using namespace RJFRC2011;

/*!
 \brief Constructor.
 \details Initialize the relays and limit switches to their appropriate ports: see \a MANIPULATOR_TOP_RELAY_PORT , \a MANIPULATOR_BOTTOM_RELAY_PORT , \a MANIPULATOR_ELEVATION_RELAY_PORT , \a MANIPULATOR_ELEVATION_BOTTOM_LIMIT_SWITCH_PORT , and \a MANIPULATOR_ELEVATION_TOP_LIMIT_SWITCH_PORT .
*/
RJFRC2011::Manipulator::Manipulator()
{
	manipulatorTop = new Relay(MANIPULATOR_TOP_RELAY_PORT, Relay::kBothDirections);
	manipulatorBottom = new Relay(MANIPULATOR_BOTTOM_RELAY_PORT, Relay::kBothDirections);
	manipulatorElevation = new Relay(MANIPULATOR_ELEVATION_RELAY_PORT, Relay::kBothDirections);
	
	manipulatorElevationBottomLimitSwitch = new DigitalInput(MANIPULATOR_ELEVATION_BOTTOM_LIMIT_SWITCH_PORT);
	manipulatorElevationTopLimitSwitch = new DigitalInput(MANIPULATOR_ELEVATION_TOP_LIMIT_SWITCH_PORT);
}

/*!
 \brief Destructor.  Kill stuff.
 */
RJFRC2011::Manipulator::~Manipulator()
{
	delete manipulatorElevationTopLimitSwitch;
	delete manipulatorElevationBottomLimitSwitch;
	delete manipulatorTop;
	delete manipulatorBottom;
	delete manipulatorElevation;
}

/*!
 \brief Suck in the tube
 \details Set both manipulator relays to reverse to suck in the tube.
 */
void RJFRC2011::Manipulator::inputTube()
{
	manipulatorTop->Set(Relay::kReverse);		// top part backward
	manipulatorBottom->Set(Relay::kReverse);	// bottom part backward
}

/*!
 \brief Rotate the tube downward
 \details Set the top manipulator relay to reverse and the bottom one forward.
 */
void RJFRC2011::Manipulator::rotateTube()
{
	manipulatorTop->Set(Relay::kForward);		// turn top part forward
	manipulatorBottom->Set(Relay::kReverse);	//turn bottom part backward
}

/*!
 \brief Spit out the tube
 \details Set both manipulator relays forward to spit out the tube.
 */
void RJFRC2011::Manipulator::ejectTube()
{
	manipulatorTop->Set(Relay::kForward);		// top part forward
	manipulatorBottom->Set(Relay::kForward);	// bottom part forward
}

/*!
 \brief Elevate or lower the manipulator
 \details If the user is pushing the joystick forward, go down until the lower limit switch is tripped.  If they're pulling it backward, go up until the upper limit switch is triggered.
 \param val The user input.
 */
void RJFRC2011::Manipulator::elevate(float val)
{
	// As of yet, limit switches are not implemented (commented out)
	// move shelf up/down with y_r
	// if user wants to go up, and we haven't hit the top limit switch yet, go up
	if (val > 0.35)// && manipulatorElevationTopLimitSwitch->Get() != 1)
	{
		manipulatorElevation->Set(Relay::kForward);
	}
	// if user wants to go down, and we haven't hit the bottom limit switch yet, go down
	else if (val < -0.35)// && manipulatorElevationBottomLimitSwitch->Get() != 1)
	{
		manipulatorElevation->Set(Relay::kReverse);
	}
	// if no response from driver, turn it off
	else
	{
		manipulatorElevation->Set(Relay::kOff);
	}
}

/*!
 \brief Stop all movement of the manipulator
 \details Turn off the manipulator relays.
 */
void RJFRC2011::Manipulator::stopManipulatorAction()
{
	manipulatorTop->Set(Relay::kOff);
	manipulatorBottom->Set(Relay::kOff);
}

/*!
 \brief Stop all elevation of the manipulator
 \details Turn off the manipulator elevation relays.
 */
void RJFRC2011::Manipulator::stopManipulatorElevation()
{
	manipulatorElevation->Set(Relay::kOff);
}
