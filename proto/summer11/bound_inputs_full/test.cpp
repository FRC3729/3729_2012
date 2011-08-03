#include "bindings.h"
#include <iostream>
using namespace std;

int main()
{
  reload_all_bindings();
  cout << AUTO_DRIVE_SPEED << endl;
  return 0;
}
