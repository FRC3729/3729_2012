/*
 *  library.h
 *
 *  Created by Matthew Haney on 6/17/09.
 *  Copyright 2009 Screaming Chicken Design Corporation. All rights reserved.
 *
 */

/*!
	\file library.h
	\brief A useful library of user-defined functions
 */
/*!
	\fn show(string ps, int times = 0, ostream & os = std::cout)
	\param ps the string to display
	\param times the number of times to show the string
	\param os the output stream to output to
	\brief Uses an \a ostream_iterator to display a \a string a certain number of times
*/
/*!
	\fn rshow(string ps, int times = 0, ostream & os = std::cout)
	\param ps the string to display
	\param times the number of times to show the string
	\param os the output stream to output to
	\brief Uses an \a ostream_iterator to display a \a string BACKWARDS a certain number of times
 */
/*!
	\fn wait(int seconds)
	\param seconds the number of seconds to wait
	\brief Wait for a certain number of seconds.  Kind of obsolete (use sleep(long) or usleep(long) from unistd.h)
 */
/*!
	\fn swap(Any & a, Any & b)
	\param a the first value to swap
	\param b the second value to swap
	\brief Swap two values by reference
 */
#ifndef _LIBRARY_H_
#define _LIBRARY_H_
#include <string>
#include <iostream>
#include <iterator>
#include <sstream>
#include <ctime>
#include <cctype>
using namespace std;

void show(string ps, int times = 0, ostream & os = std::cout);
void rshow(string ps, int times = 0, ostream & os = std::cout);
void wait(int seconds);
template <class Any>
void swap(Any & a, Any & b);
void ToLower(string & str);
void ToUpper(string & str);
//! Clear a line of input from cin
inline void eatline() {while (cin.get() != '\n') continue;}
int parseInt(string str);
string parseString(int num);

void show(string ps, int times, ostream & os)
{
	ostream_iterator<char, char> out_str(os, "");
	if (times == 0)
	{
		copy(ps.begin(), ps.end(), out_str);
		endl(os);
	}
	else if (times > 0)
	{
		for (int i = 0; i < times; i++)
		{
			copy(ps.begin(), ps.end(), out_str);
			endl(os);
		}
	}
}
void rshow(string ps, int times, ostream & os)
{
	ostream_iterator<char, char> out_str(os, "");
	if (times == 0)
	{
		copy(ps.rbegin(), ps.rend(), out_str);
		endl(os);
	}
	else if (times > 0)
	{
		for (int i = 0; i < times; i++)
		{
			copy(ps.rbegin(), ps.rend(), out_str);
			endl(os);
		}
	}
}
void wait (int seconds)
{
	clock_t endwait = clock() + (seconds * CLOCKS_PER_SEC);
	while (clock() < endwait) { continue; }
}
template <class Any>
void swap(Any & a, Any & b)
{
	Any temp;
	temp = a;
	a = b;
	b = temp;
}
void ToLower(string & str)
{
	std::transform(str.begin(), str.end(), str.begin(), (int(*)(int)) tolower);
}
void ToUpper(string & str)
{
	std::transform(str.begin(), str.end(), str.begin(), (int(*)(int)) toupper);
}
int parseInt(string str)
{
  istringstream parser(str);
  int num = 0;
  parser >> num;
  return num;
}
string parseString(int num)
{
  ostringstream parser("");
  parser << num;
  return parser.str();
}

//Work of FLoyd Davidson, posted on groups.google.com
#ifndef _KBHIT_
#define _KBHIT_
#include <stdio.h> 
#include <unistd.h> 
#include <termios.h> 
#include <sys/ioctl.h> 
#include <sys/time.h> 
#include <sys/types.h> 
//! Return the numer of characters available to read, or -1 if an error occurred
int kbhit(void); 
#define CMIN  1 
#ifdef CTIME 
#undef CTIME 
#endif 
#define CTIME 1 
/* 
 *  kbhit()  --  a keyboard lookahead monitor 
 * 
 *  returns the number of characters available to read. 
 */ 
int 
kbhit(void) 
{ 
	int                   cnt = 0; 
	int                   error; 
	static struct termios Otty, Ntty; 
	tcgetattr(0, &Otty); 
	Ntty = Otty; 
	Ntty.c_iflag          = 0;       /* input mode        */ 
	Ntty.c_oflag          = 0;       /* output mode       */ 
	Ntty.c_lflag         &= ~ICANON; /* raw mode              */ 
	Ntty.c_cc[VMIN]       = CMIN;    /* minimum chars to wait for */ 
	Ntty.c_cc[VTIME]      = CTIME;   /* minimum wait time */ 
	if (0 == (error = tcsetattr(0, TCSANOW, &Ntty))) {
		struct timeval      tv;
		error     += ioctl(0, FIONREAD, &cnt);
		error     += tcsetattr(0, TCSANOW, &Otty);
		tv.tv_sec  = 0; 
		tv.tv_usec = 100;   /* insert a minimal delay */ 
		select(1, NULL, NULL, NULL, &tv); 
	}
	return (error == 0 ? cnt : -1 ); 
}

#ifndef _GETCH_
#define _GETCH_
/* 
 *  getch()  --  a blocking single character input from stdin 
 * 
 *  Returns a character, or -1 if an input error occurs. 
 * 
 *  Conditionals allow compiling with or without echoing of 
 *  the input characters, and with or without flushing pre-existing 
 *  existing  buffered input before blocking. 
 * 
 */ 
//! Blocking single-char input from stdin
int getch(void);
int 
getch(void) 
{ 
	char                  ch; 
	int                   error; 
	static struct termios Otty, Ntty; 
	fflush(stdout); 
	tcgetattr(0, &Otty); 
	Ntty = Otty; 
	Ntty.c_iflag  =  0;           /* input mode           */ 
	Ntty.c_oflag  =  0;           /* output mode          */ 
	Ntty.c_lflag &= ~ICANON;  /* line settings        */ 
#if 1 
	/* disable echoing the char as it is typed */ 
	Ntty.c_lflag &= ~ECHO;    /* disable echo         */ 
#else 
	/* enable echoing the char as it is typed */ 
	Ntty.c_lflag |=  ECHO;        /* enable echo          */ 
#endif 
	Ntty.c_cc[VMIN]  = CMIN;      /* minimum chars to wait for */ 
	Ntty.c_cc[VTIME] = CTIME;     /* minimum wait time    */ 
#if 1 
	/* 
	 * use this to flush the input buffer before blocking for new input 
	 */ 
#define FLAG TCSAFLUSH 
#else 
	/* 
	 * use this to return a char from the current input buffer, or block if 
	 * no input is waiting. 
	 */ 
#define FLAG TCSANOW 
#endif 
	if (0 == (error = tcsetattr(0, FLAG, &Ntty))) { 
		error  = read(0, &ch, 1 );                /* get char from stdin */ 
		error += tcsetattr(0, FLAG, &Otty);   /* restore old settings */ 
	} 
	return (error == 1 ? (int) ch : -1 ); 
} 
#endif
#endif
#endif
