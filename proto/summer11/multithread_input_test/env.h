#include <map>
#include <string>
#include "exception.h"
using namespace std;

namespace env_vars
{
  // Global variable storing environment variable pairs
  multimap<string, string> vars;
}

// Return environment variable map
multimap<string, string> get_env_vars() { return env_vars::vars; }

// Parse environment variables from a two-dimensional array
multimap<string, string> env_parse(char * env[])
{
  // Storage for variable name
  string var("");
  // Storage for variable value
  string value("");
  // Are we left or right of the '=' sign?
  int section = 0;
  // Current 'X=Y' declaration (from env[x] argument)
  char * current_declaration;
  // Current char (from current_declaration[x] argument)
  char ch;
  // Cycle through declarations
  for	(int varnum = 0; varnum < MAX_BINDINGS; varnum++)
    {
    current_declaration = env[varnum];
    // Cycle through characters in declaration
    for (int i = 0; current_declaration[i] != '\0' && current_declaration[i] != '\n'; i++)
      {
      ch = current_declaration[i];
      //* DEBUG */ cout << ch;
      // if it's an equals sign, switch from parsing variable name to variable value
      if (ch == '=')
	{
	section = 1;
	continue;
      }
      switch (section)
	{
      case 0:
	// If left of '=', add character to variable name
	var.push_back(ch);
	break;
      case 1:
	// If right of '=', add character to variable value
	value.push_back(ch);
	break;
      default:
	// Something is wrong... reset and try again
	section = 0;
	i--;
	break;
      }
    }
    // When we're done parsing through the declaration, store the name/value pair in the global map
    env_vars::vars.insert(pair<string, string>(var, value));
    // Next reset the variables used
    section = 0;
    var.assign("");
    value.assign("");
  }
  // All data is stored in a global variable, but we'll return it if the user wants to use it
  return env_vars::vars;
}

// Retrieve value of an environment variable based on
string env_retrieve(string key)
{
  // Check to see if key exists - remember, it's case sensitive!
  if (env_vars::vars.find(key) != env_vars::vars.end())
  {
    // Get range of values that match requested key
    pair<multimap<string, string>::iterator, multimap<string, string>::iterator> range = env_vars::vars.equal_range(key);
    
    // Return the first value (there shouldn't be more than one)
    return (*range.first).second;
  }
  else
  {
    // Let the user know which key didn't work in an alarming way ;)
    throw EXCEPTION<string>(key);
  }  
}
