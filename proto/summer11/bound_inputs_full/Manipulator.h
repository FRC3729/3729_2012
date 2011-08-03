/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Manipulator.h
 \brief File containing definition of \a Manipulator class, which is used to simulate the manipulator in both Autonomous and Periodic modes.
 \authors Matthew Haney, Drew Lazzeri
 
 */

#ifndef _RJHS_FRC_2011_MANIPULATOR_H_
#define _RJHS_FRC_2011_MANIPULATOR_H_

#include "bindings.h"
#include "WPILib.h"

namespace RJFRC2011
{
	/*!
	 \class Manipulator
	 \brief Abstracts the manipulator.
	 \details Custom class interfacing with the manipulator on our robot through various relays and digitl inputs.  Provides multiple methods for easily manipulating the... err... manipulator.
	 */
	class Manipulator
	{
	private:
		//! Forwards/backwards relay controlling motion of the top two wheels of the manipulator.
		Relay * manipulatorTop;
		//! Forwards/backwards relay controlling motion of the bottom two wheels of the manipulator.
		Relay * manipulatorBottom;
		//! Forwards/backwards relay controlling position of the manipulator.
		Relay * manipulatorElevation;
		//! Limit switch at the bottom of the manipulator elevator.
		DigitalInput * manipulatorElevationBottomLimitSwitch;
		//! Limit switch at the top of the manipulator elevator.
		DigitalInput * manipulatorElevationTopLimitSwitch;
	public:
		Manipulator();
		~Manipulator();
		void inputTube();
		void rotateTube();
		void ejectTube();
		void elevate(float val); 
		void stopManipulatorAction();
		void stopManipulatorElevation();
		/*! 
		 \brief Safely return address of the top limit switch
		 \return The address, as a pointer-to-DigitalInput
		 */
		DigitalInput * GetTopLimitSwitchAddress() const { return manipulatorElevationTopLimitSwitch; }
		/*! 
		 \brief Safely return address of the bottom limit switch
		 \return The address, as a pointer-to-DigitalInput
		 */
		DigitalInput * GetBottomLimitSwitchAddress() const { return manipulatorElevationBottomLimitSwitch; }
	};
};

#endif
