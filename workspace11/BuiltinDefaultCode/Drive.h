/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file Drive.h
 \brief File containing definition of \a Drive class, which is used in the main program to drive the robot in both Autonomous and Teleoperated modes with no waste of memory.
 \authors Matthew Haney, Drew Lazzeri
 */

#ifndef _RJHS_FRC_2011_DRIVE_H_
#define _RJHS_FRC_2011_DRIVE_H_

#include "WPILib.h"
#include "macros.h"

namespace RJFRC2011
{
	/*!
	 \brief Class abstracting the drive system.
	 \details Basically a wrapper for the WPILib-defined \a RobotDrive class, with our own fancy ramping capabilities put in.
	 */
	class Drive
	{
	private:
		//! WPILib-defined class for abstracting the drive mechanism.  Our "Drive" class basically clarifies and ramps the inputs provided by RobotDrive.
		RobotDrive * _drive;
		//! The last sent speed value (used for ramping).
		float _speed_prev;
		//! The last sent turn value (used for ramping).
		float _turn_prev;
	public:
		Drive();
		~Drive();
		void drive(float speed, float turn);
		void drive_noramp(float speed, float turn);
		void tank_drive(float left, float right);
		float ramp(float desired_output, float current_output);
	};
};

#endif
