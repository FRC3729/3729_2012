/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012.loopable;

/**
 *
 * @author Morgan
 */
public interface FRCLoopable {
    abstract void setup();
    abstract void loop_periodic();
    abstract void loop_continuous();
}
