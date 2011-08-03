/*
 RJFRC - The code used by Regis Jesuit High School's FIRST Robotics Competition team #3729 over the years, starting from 2011 (rookie year).
 Copyright (C) 2011 Regis Jesuit High School
 
 This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*!
 \file bindings.h
 \brief File containing a bunch of macros to de-clutter our other files.  Also manages control and I/O port bindings.
 
 The #define macros in here are adjustment macros (courtesy of Adam Bryant) for use with the InterLink Elite RC controller.  These macros are necessary because the joysticks on this controller are slightly off-center and do not give values in the range of [-1.0, 1.0].
 The other bindings are dynamically loaded from a file so that we don't have to recompile after changing a port configuration.
 
 */

#ifndef _RJHS_FRC_2011_BINDINGS_H_
#define _RJHS_FRC_2011_BINDINGS_H_

#include <string>
#include <fstream>
#include <sstream>
#include "env.h"
using namespace std;

const string ports_bindings_filename = "io.txt";
const string misc_bindings_filename = "misc.txt";
const int MAX_PORT_BINDINGS = 16;
const int MAX_MISC_BINDINGS = 5;
ifstream fin;

// 15 total right now
const string possible_port_bindings[MAX_PORT_BINDINGS] = {
	"AUTONOMOUS_LANE_SWITCH_PORT",
	"AUTONOMOUS_FORK_SWITCH_PORT",
	"MANIPULATOR_ELEVATION_TOP_LIMIT_SWITCH_PORT",
	"MANIPULATOR_ELEVATION_BOTTOM_LIMIT_SWITCH_PORT",
	"LIGHT_SENSOR_LEFT_PORT",
	"LIGHT_SENSOR_CENTER_PORT",
	"LIGHT_SENSOR_RIGHT_PORT",
	"MINIBOT_SHELF_RELAY_PORT",
	"MANIPULATOR_TOP_RELAY_PORT",
	"MANIPULATOR_BOTTOM_RELAY_PORT",
	"MANIPULATOR_ELEVATION_RELAY_PORT",
	"DRIVE_FRONT_LEFT_JAGUAR_PORT",
	"DRIVE_FRONT_RIGHT_JAGUAR_PORT",
	"DRIVE_BACK_LEFT_JAGUAR_PORT",
	"DRIVE_BACK_RIGHT_JAGUAR_PORT"
};

int port_bindings[MAX_PORT_BINDINGS];

const string possible_misc_bindings[MAX_MISC_BINDINGS] = {
	"DEFAULT_WATCHDOG_TIME",
	"AUTO_DRIVE_SPEED",
	"AUTO_TURN_SPEED",
	"AUTO_BRAKE_SPEED"
};

float misc_bindings[MAX_MISC_BINDINGS];

int reload_port_bindings(string filename = ports_bindings_filename);
int reload_misc_bindings(string filename = misc_bindings_filename);

/*!
 \brief Convert an arbitrary type to a printable string, since this isn't Python
 \param val The number to be converted
 \returns The string representation of the passed number
 */

template <typename T>
const char * topchar(T val)
{
	ostringstream out_str("");
	out_str << val;
	return out_str.str().c_str();
}

int parseInt(string str)
{
	istringstream parser(str);
	int num = 0;
	parser >> num;
	return num;
}

float parseFloat(string str)
{
	istringstream parser(str);
	float num = 0;
	parser >> num;
	return num;
}

string parseString(int num)
{
	ostringstream parser("");
	parser << num;
	return parser.str();
}

// Adjustment macros by Adam Bryant
//! Adjustment for the fact that the joystick is slightly off-center
#define YCENTER (0.03125)
//! Adjustment for the fact that the joystick is slightly off-center
#define ROTCENTER (0.0156)
//! Minimum possible X value
#define XMIN -0.641
//! Maximum possible X value
#define XMAX 0.648
//! Minimum possible Y value
#define YMIN (-0.57-YCENTER)
//! Maximum possilble Y value
#define YMAX (0.641-YCENTER)
//! Minimum possible Z value
#define ZMIN (-0.54)
//! Maximum possible Z value
#define ZMAX (0.63)
//! Minimum possible rotation value
#define ROTMIN (-0.64-ROTCENTER)
//! Maximum possible rotation value
#define ROTMAX (0.68-ROTCENTER)
//! Exponential constant for modifying input from the x-axis
#define XEXPO 0.4
//! Exponential constant for modfying input from the y-axis
#define YEXPO 0.4
//! Exponential constant for modifying input form the rotational axis
#define ROTEXPO 0.6

// Port # constants: Digital I/O
//! Port # for the physical lane choosing switch in Autonomous
int & AUTONOMOUS_LANE_SWITCH_PORT = port_bindings[0];
//! Port # for the physical fork choosing switch in Autonomous
int & AUTONOMOUS_FORK_SWITCH_PORT = port_bindings[1];
//! Port # for the limit switch at the top of the manipulator elevator's reach
int & MANIPULATOR_ELEVATION_TOP_LIMIT_SWITCH_PORT = port_bindings[2];
//! Port # for the limit switch at the bottom of the manipulator elevator's reach
int & MANIPULATOR_ELEVATION_BOTTOM_LIMIT_SWITCH_PORT = port_bindings[3];
//! Port # of the leftmost line-following light sensor
int & LIGHT_SENSOR_LEFT_PORT = port_bindings[4];
//! Port # of the center line-following light sensor
int & LIGHT_SENSOR_CENTER_PORT = port_bindings[5];
//! Port # of the rightmost line-following light sensor
int & LIGHT_SENSOR_RIGHT_PORT = port_bindings[6];

// Port # constants: Relays
//! Port # for the relay controlling the minbot shelf
int & MINIBOT_SHELF_RELAY_PORT = port_bindings[7];
//! Port # for the relay controlling the top wheels of the manipulator
int & MANIPULATOR_TOP_RELAY_PORT = port_bindings[8];
//! Port # for the relay controlling the bottom wheels of the manipulator
int & MANIPULATOR_BOTTOM_RELAY_PORT = port_bindings[9];
//! Port # for the relay controlling elevation of the manipulator
int & MANIPULATOR_ELEVATION_RELAY_PORT = port_bindings[10];

// Port # constants: PWM out
//! Port # of the front left Jaguar on the drive train
int & DRIVE_FRONT_LEFT_JAGUAR_PORT = port_bindings[11];
//! Port # of the front right Jaguar on the drive train
int & DRIVE_FRONT_RIGHT_JAGUAR_PORT = port_bindings[12];
//! Port # of the back left Jaguar on the drive train
int & DRIVE_BACK_LEFT_JAGUAR_PORT = port_bindings[13];
//! Port # of the back right Jaguar on the drive train
int & DRIVE_BACK_RIGHT_JAGUAR_PORT = port_bindings[14];

// Others
//! The default expiration time of the Watchdog timer, in seconds
float & DEFAULT_WATCHDOG_TIME = misc_bindings[0];
//! Speed at which we drive in Autonomous
float & AUTO_DRIVE_SPEED = misc_bindings[1];
//! Speed at which we turn in Autonomous
float & AUTO_TURN_SPEED = misc_bindings[2];
//! Speed at which we brake in Autonomous
float & AUTO_BRAKE_SPEED = misc_bindings[3];

/*!
 
 */
int reload_port_bindings(string filename)
{
	// We should know how many bindings we have (or at least the max)
	string from_file[MAX_PORT_BINDINGS];
	// Now read from the file
	fin.open(filename.c_str(), ios_base::in | ios_base::binary);
	if (!fin.is_open())
		cerr << "Failed to open file." << endl; 
	string str("");
	for (int i = 0; i < MAX_PORT_BINDINGS; i++)
	{
		str = "";
		getline(fin, str, '\n');
		//str.push_back('\n');
		from_file[i].assign(str);
	}
	env_parse(from_file, MAX_PORT_BINDINGS);
	str = "";
	int i = 0;
	while (i < MAX_PORT_BINDINGS)
	{
		try
		{
			for (true; i < MAX_PORT_BINDINGS; i++)
			{
				str = env_retrieve(possible_port_bindings[i]);
				port_bindings[i] = parseInt(str);
			}
		}
		catch (EXCEPTION<string> ex)
		{
			cerr << "Failed to retrieve key: " << ex << endl;
			i++;
		}
	}
	fin.close();
	return 0;
}

int reload_misc_bindings(string filename)
{
	// We should know how many bindings we have (or at least the max)
	string from_file[MAX_MISC_BINDINGS];
	// Now read from the file
	fin.open(filename.c_str(), ios_base::in | ios_base::binary);
	if (!fin.is_open())
		cerr << "Failed to open file." << endl; 
	string str("");
	for (int i = 0; i < MAX_MISC_BINDINGS; i++)
	{
		str = "";
		getline(fin, str, '\n');
		//str.push_back('\n');
		from_file[i].assign(str);
	}
	env_parse(from_file, MAX_MISC_BINDINGS);
	str = "";
	int i = 0;
	while (i < MAX_MISC_BINDINGS)
	{
		try
		{
			for (true; i < MAX_MISC_BINDINGS; i++)
			{
				str = env_retrieve(possible_misc_bindings[i]);
				misc_bindings[i] = parseFloat(str);
			}
		}
		catch (EXCEPTION<string> ex)
		{
			cerr << "Failed to retrieve key: " << ex << endl;
			i++;
		}
	}
	fin.close();
	return 0;
}

void reload_all_bindings()
{
	// Call all "reload_X_bindings" functions using the default filenames
	reload_port_bindings();
	reload_misc_bindings();
	// reload_control_bindings();
}

#endif
