/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */

package edu.first3729.frc2012.periodic.gamemode;

import edu.first3729.frc2012.FRCRobot;

/**
 *
 * @author Morgan
 */
public class FRCGameModeDisabled extends FRCGameMode {

    public FRCGameModeDisabled(FRCRobot robot) {
        // Initialize superclass
        super(robot);
    }

    public void loop_continuous() {
        // Nothing
    }
    
    public void loop_periodic() {
        // Nothing
    }
}
