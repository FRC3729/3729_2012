#include <unistd.h>
#include <pthread.h>
#include <iostream>
#include <cmath>
#define MAX_BINDINGS 4
#include "env.h"
#include "library.h"
#include <fstream>
using namespace std;

string bindings_filename = "bindings.txt";
ifstream fin;

void * get_input(void * unused_args);
int reload_bindings(string filename);
int go_north();
int go_south();
int go_east();
int go_west();

char input = ' ';
int(*bound_w)();
int(*bound_a)();
int(*bound_s)();
int(*bound_d)();

int go_north()
{
	cout << "You go north.  Yay!" << endl;
	return 0;
}

int go_south()
{
	cout << "You go south.  Yay!" << endl;
	return 0;
}

int go_east()
{
	cout << "You go east.  Yay!" << endl;
	return 0;
}

int go_west()
{
	cout << "You go west.  Yay!" << endl;
	return 0;
}

int reload_bindings(string filename)
{
	// We should know how many bindings we have (or at least the max)
	string from_file[4];
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
	char * env[4];
	for (int i = 0; i < MAX_BINDINGS; i++)
	{
		env[i] = const_cast<char *>(from_file[i].c_str());
	}
	env_parse(env);
	str = "";
	try
	{
		// Grrr...bindings must be hard-coded to work best
		// 'w' binding
		str = env_retrieve("w");
		if (str == "go_north")
			bound_w = go_north;
		else if (str == "go_south")
			bound_w = go_south;
		else if (str == "go_east")
			bound_w = go_east;
		else if (str == "go_west")
			bound_w  = go_west;
		str = env_retrieve("a");
		if (str == "go_north")
			bound_a = go_north;
		else if (str == "go_south")
			bound_a = go_south;
		else if (str == "go_east")
			bound_a = go_east;
		else if (str == "go_west")
			bound_a  = go_west;
		str = env_retrieve("s");
		if (str == "go_north")
			bound_s = go_north;
		else if (str == "go_south")
			bound_s = go_south;
		else if (str == "go_east")
			bound_s = go_east;
		else if (str == "go_west")
			bound_s  = go_west;
		str = env_retrieve("d");
		if (str == "go_north")
			bound_d = go_north;
		else if (str == "go_south")
			bound_d = go_south;
		else if (str == "go_east")
			bound_d = go_east;
		else if (str == "go_west")
			bound_d  = go_west;
		
	}
	catch (EXCEPTION<string> ex)
	{
		cerr << ex << endl;
		return 1;
	}
	return 0;
}

void * get_input(void * unused_args)
{
	int d_kbhit = 0;
	int old_kbhit = 0;
	while (input != 'x')
	{
		d_kbhit = abs(old_kbhit - kbhit());
		old_kbhit = kbhit();
		cout << d_kbhit;
		if (d_kbhit > 0)
		{
			input = cin.peek();
		}
		else
		{
			input = ' ';
		}

	}
	return NULL;
}

int main(int argc, char * argv[], char * p_env[])
{
	if (reload_bindings(bindings_filename) != 0)
		cout << "Could not successfully load bindings from file " << bindings_filename << "." << endl;
	pthread_t input_thread;
	void * unused;
	pthread_create(&input_thread, NULL, get_input, unused);
	while (input != 'x')
	{
		if (input == 'w')
			bound_w();
		else if (input == 'a')
			bound_a();
		else if (input == 's')
			bound_s();
		else if (input == 'd')
			bound_d();
	}
	pthread_join(input_thread, &unused);
	return 0;
}
