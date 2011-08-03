//Exception.h - template Exception class
#ifndef _HANEY_EXCEPTION_H_
#define _HANEY_EXCEPTION_H_
#include <iostream>
using namespace std;

template <typename T>
class EXCEPTION
{
private:
  const T thrown_value;
  const int err;
public:
  ~EXCEPTION() {}
  EXCEPTION() : thrown_value(0), err(0) {}
  EXCEPTION(T value, int e = 0) : thrown_value(value), err(e) {}
  const T getval() const { return thrown_value; }
  const int errcode() const { return err; }
  friend ostream & operator<<(ostream & os, EXCEPTION<T> ex)
  {
    if (ex.err != 0)
      os << "Error code " << ex.errcode() << ": " << ex.getval();
    else
      os << ex.getval();
	return os;
  }
};

#endif
