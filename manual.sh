#! /usr/bin/env bash
# This is a command to generate documentation.  It's a tad long, so this script is here because I'm lazy.

# If no args, print usage
if [ "$#" = "0" ]; then
    echo "Usage: manual.sh XY, where XY are the last two digits of the year for which you want to generate documentation";
    exit;
else
    # Otherwise, the first arg is the year
    YEAR="$1"
fi
# Define output directory
DOCS="./docs$YEAR";
# In 2011 we used Eclipse, later we used NetBeans
if [ "$YEAR" = "11" ]; then
    SRCDIR="./FRC20$YEAR/BuiltinDefaultCode"
else
    SRCDIR="./FRC20$YEAR/src";
fi
# Remove directory if it exists
if [ -d "$DOCS" ]; then 
    rm -r $DOCS
fi
# Dynamic Doxygen config tags
# Source code directory, determined by year
echo "INPUT               = $SRCDIR" > projsrc.txt;
# Output directory, determined by year
echo "OUTPUT_DIRECTORY   = ./docs$YEAR" > projdir.txt;
# Project number (just the year)
echo "PROJECT_NUMBER     = 20$YEAR" > projnum.txt;
# Project brief
echo "PROJECT_BRIEF      = \"Technical documentation of source code used by the Regis Jesuit FIRST Robotics Competition team #3729 during the 20$YEAR season.\"" > projbrief.txt;
# Combine the above with the rest of the DOXYFILE
cat DOXYFILE projsrc.txt projdir.txt projnum.txt projbrief.txt > DOXYFILE_TEMP;
# Run Doxygen
doxygen DOXYFILE_TEMP;
# Clean up
rm -f projsrc.txt projdir.txt projnum.txt projbrief.txt lua.txt DOXYFILE_TEMP;
# Make beautiful PDF documentation - REQUIRES PDFLATEX
cd $DOCS/latex;
make;
# Return to root directory
cd ../..;
# Meh... we don't need this
# simplify_man_pages $YEAR;
