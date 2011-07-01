#! /usr/local/Cellar/python/2.7.1/bin/python
"""
Test for the moderation of interface between input and jaguar motor controller
"""
def mod1(desired_output, current_output, increment = .005):
    """Takes in the output we want to send, and the one we are sending, and outputs a medium based on the size of the float increment
    """
    if desired_output < current_output:
        return current_output - desired_output * increment
    if desired_output > current_output:
        return desired_output * increment + current_output
    else:
        return current_output
