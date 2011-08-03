/*!
 \file proto/summer11/bound_input_test/main.cpp
 This file is a bunch of functions that demonstrate an idea I had for dynamically loading
 control bindings from a file - that way, controls coul be modified via FTP wihtout having
 to recompile.  This file isn't intended to be compiled, as it probably wouldn't compile under
 straight g++ or WindRiver.
 I think this is a good prototype - I'm gonna integrate it into our 2011 code and see how it
 feels.  I won't be able to compile or run it yet, but whatever.
 */
#include "WPILib.h"
#include <cmath>
#define MAX_BINDINGS 10
#include "env.h"
#include "library.h"
#include <fstream>
using namespace std;

const string bindings_filename = "bindings.txt";
ifstream fin;

void get_input();
int reload_bindings(string filename = bindings_filename);
int tube_out();
int tube_in();
int shelf_out();
int shelf_in();
inline int spin() { return 0; };

int(*button0)();
int(*button1)();
int(*button2)();
int(*button3)();
int(*button4)();
int(*button5)();
int(*button6)();
int(*button7)();
int(*button8)();
int(*button9)();

int (*buttons[10])() = { button0, button1, button2, button3, button4, button5, button6, button7, button8, button9 };

int reload_bindings(string filename)
{
	// We should know how many bindings we have (or at least the max)
	string from_file[MAX_BINDINGS];
	// Now read from the file
	fin.open(filename.c_str(), ios_base::in | ios_base::binary);
	if (!fin.is_open())
		cerr << "Failed to open file." << endl; 
	string str("");
	for (int i = 0; i < MAX_BINDINGS; i++)
	{
		str = "";
		getline(fin, str, '\n');
		//str.push_back('\n');
		from_file[i].assign(str);
	}
	env_parse(from_file);
	str = "";
	string button("button");
	try
	{
		for (int current_button = 1; current_button <= 10; current_button++)
		{
			str = env_retrieve(parseString(current_button));
			if (str == "tube_out")
				buttons[current_button - 1] = tube_out;
			else if (str == "tube_in")
				buttons[current_button - 1] = tube_in;
			else if (str == "shelf_in")
				buttons[current_button - 1] = shelf_in;
			else if (str == "shelf_out")
				buttons[current_button - 1] = shelf_out;
			else
				buttons[current_button - 1] = spin;
		}
	}
	catch (EXCEPTION<string> ex)
	{
		cerr << "Failed to retrieve key: " << ex << endl;
		return 1;
	}
	return 0;
}

int tube_out()
{
	// Spit out tube
}

int tube_in()
{
	// Suck in tube
}
int shelf_out()
{
	// Send out minibot shelf
}

int shelf_in()
{
	// Pull in minibot shelf
}

void get_input()
{
	//In here we could do something like:
	for (int i = 0; i < 10; i++)
	{
		if (_joystick->GetRawButton(i) == 1)
		{
			buttons[i]();
		}
	}
}

int main(int argc, char * argv[], char * p_env[])
{
	if (reload_bindings(bindings_filename) != 0)
	{
		cout << "Could not successfully load bindings from file " << bindings_filename << "." << endl;
		return 1;
	}
	while (true) { get_input(); }
	return 0;
}
