/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file BuiltinDefaultCode.cpp
 \brief The file containing the class BuiltinDefaultCode.  We didn't bother changing the name...
 \details The main source code file for Regis Jesuit High School FRC Team #3729.
 \authors Matthew Haney, Drew Lazzerri
 */

#include "bindings.h"
#include "WPILib.h"
#include "Autonomous.h"
#include "Teleoperated.h"
#include "Controller.h"
#include <sstream>

using namespace RJFRC2011;

/*!
 \class BuiltinDefaultCode : public IterativeRobot
 \brief Our big class that does everything.  We didn't bother renaming it.
 
 BuiltinDefaultCode is technically the name of the example class that we first opened, but once we started coding we realized that we couldn't rename the project and didn't want to reconfigure all the build settings from scratch.  So here we are.
 
 */

class BuiltinDefaultCode : public IterativeRobot
{
private:
	//! Class abstracting drive system
	Drive * drive;
	//! Class abstracting manipulator
	Manipulator * manipulator;
	//! Class abstracting the controller
	Controller * controller;
	//! The screen.  Yup.
	DriverStationLCD * screen;
	
	//! Driver Station object; used so we can send info back to the user (at least, in theory)
	DriverStation * ds;
	//! Our rear-mounted camera, used to see stuff in style
	AxisCamera * cam;
	
	bool timerHasStarted;
	
	DriverStationEnhancedIO * dseio;
	//! Class running the Autonomous period
	Autonomous * auto_machine;
	//! Class running the Teleoperated period
	Teleoperated * teleop_machine;
	
	//! Used to time movements in Autonomous
	Timer * timer;
	
	//Debug info
	int nAutoLoops, nTeleLoops, nDisabledLoops, end_loops;
public:
	/*!
	 \brief Constructor.
	 \details Set up things before robot does anything; mostly this allocates memory for things.
	 */
	BuiltinDefaultCode(void)
	{
		// GRRR... camera not working...
		GetWatchdog().SetExpiration(10);
		GetWatchdog().SetEnabled(false);
		cam = &AxisCamera::GetInstance();
		cam->WriteResolution(AxisCamera::kResolution_640x480);
		cam->WriteCompression(30);
		cam->WriteBrightness(50);
		//cam->WriteMaxFPS(10);
		cam->WriteExposureControl(AxisCamera::kExposure_Automatic);
		cam->WriteWhiteBalance(AxisCamera::kWhiteBalance_Automatic);
		//cam->WriteMaxFPS(1);
		drive = new Drive();
		manipulator = new Manipulator();
		controller = new Controller();
		screen = DriverStationLCD::GetInstance();
		// Set up state machines
		auto_machine = new Autonomous(drive, manipulator, controller, screen);
		teleop_machine = new Teleoperated(drive, manipulator, controller, screen);
		timer = new Timer();
		// Acquire the Driver Station object
		ds = DriverStation::GetInstance();
		nAutoLoops = nTeleLoops = nDisabledLoops = 0;
		timerHasStarted = false;
		GetWatchdog().SetEnabled(true);
	}
	
	//! Destructor.  Called when a class object expires.  Used so we don't waste memory.
	~BuiltinDefaultCode()
	{
		delete auto_machine;
		delete teleop_machine;
		delete drive;
		delete controller;
		delete manipulator;
		delete timer;
	}
	
	/********************************** Init Routines *************************************/
	
	//! Actions which would be performed once (and only once) upon initialization of the robot.
	void RobotInit(void)
	{
		reload_all_bindings();
		GetWatchdog().SetExpiration(DEFAULT_WATCHDOG_TIME);
		// Actions which would be performed once (and only once) upon initialization of the
		// robot would be put here.
	}
	
	//! Code called at the beginning of Disabled Mode.
	void DisabledInit(void)
	{
		screen->PrintfLine(DriverStationLCD::kUser_Line1, "DisabledInit() completed.");
		screen->UpdateLCD();
		GetWatchdog().Feed();
		nDisabledLoops = 0;
	}
	
	//! Code called at the beginning of Autonomous Mode.
	void AutonomousInit(void)
	{
		timerHasStarted = false;
		timer->Reset();
		GetWatchdog().SetEnabled(false);
		screen->PrintfLine(DriverStationLCD::kUser_Line1, "AutonomousInit() completed.");
		screen->UpdateLCD();
		auto_machine->Init();
		nAutoLoops = 0;
	}
	
	
	//! Code called at the beginning of Teleoperated Mode.
	void TeleopInit(void)
	{
		GetWatchdog().SetEnabled(true);
		GetWatchdog().SetExpiration(DEFAULT_WATCHDOG_TIME);
		screen->PrintfLine(DriverStationLCD::kUser_Line1, "TeleopInitInit() completed.");
		screen->UpdateLCD(); 
		GetWatchdog().Feed();
		teleop_machine->Init();
		nTeleLoops = 0;
	}
	
	/********************************** Periodic Routines *************************************/
	
	//! The code called in a loop during Disabled Mode.  Left as the default, minus the loop counter.
	void DisabledPeriodic(void)
	{
		screen->PrintfLine(DriverStationLCD::kUser_Line2, "In DisabledPeriodic.  Yup.  Loop #", nDisabledLoops);
		screen->PrintfLine(DriverStationLCD::kUser_Line6, topchar(ds->GetBatteryVoltage()));
		screen->UpdateLCD();
		GetWatchdog().Feed();
		// Basically, sit idle and keep the watchdog running.
	}
	
	//! The code called in a loop during Autonomous Mode.
	void AutonomousPeriodic(void)
	{
		screen->PrintfLine(DriverStationLCD::kUser_Line2, topchar(nAutoLoops));
		screen->PrintfLine(DriverStationLCD::kUser_Line6, topchar(ds->GetBatteryVoltage()));
		screen->UpdateLCD();
		if (!auto_machine->DONE)
			auto_machine->Go();
		else if (auto_machine->DONE)
		{
			if (!timerHasStarted)
			{
				timer->Start();
				timerHasStarted = true;
			}
			if (timer->Get() < 4.0)
			{
				this->screen->PrintfLine(DriverStationLCD::kUser_Line1, "retreat");
				this->screen->UpdateLCD();
				this->drive->drive(-0.4, 0);
			}
			else if (timer->Get() > 4.0 && timer->Get() < 6.0)
			{
				this->drive->tank_drive(-0.5, 0.5);
				this->screen->PrintfLine(DriverStationLCD::kUser_Line1, "done");
				this->screen->UpdateLCD();
			}
			else
			{
				timer->Stop();
				// twiddle_thumbs()
			}
		}
		auto_machine->printLightSensors();
		//auto_machine->testLimitSwitches();
		nAutoLoops++;
	}
	
	//! The code called in a loop during Teleoperated Mode.
	void TeleopPeriodic(void)
	{
		screen->PrintfLine(DriverStationLCD::kUser_Line2, topchar(nTeleLoops));
		screen->PrintfLine(DriverStationLCD::kUser_Line6, topchar(ds->GetBatteryVoltage()));
		screen->UpdateLCD();
		GetWatchdog().Feed();
		teleop_machine->Go();
		nTeleLoops++;
	}
};

START_ROBOT_CLASS(BuiltinDefaultCode);
