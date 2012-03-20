/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.camera.AxisCamera;

public class FRCParams {
    // Port # constants: PWM out

    public static final int shooter1_victor_port = 5;
    public static final int shooter2_victor_port = 6;
    
    public static final int shooter1_jaguar_port = 5;
    public static final int shooter2_jaguar_port = 6;
    
    public static final int intake_relay_port = 1;
    public static final int elevator_relay_port = 2;
    public static final int net_relay_port = 4;
    public static final int intake_sensor_digin_port = 1;
    public static final int bridge_limit_digin_port = 2;
    public static final String camera_IP = "10.37.29.11";
    public static final AxisCamera.ResolutionT camera_resolution = AxisCamera.ResolutionT.k640x480;
    public static final int camera_FPS = 24;
    // Others
    //! The default expiration time of the Watchdog timer, in seconds
    public static final double default_watchdog_time = 3.0;
    //! Speed at which we drive in Autonomous
    public static final double auto_drive_speed = 0.35;
    //! Speed at which we turn in Autonomous
    public static final double auto_turn_speed = 0.65;
    //! Speed at which we brake in Autonomous
    public static final double auto_brake_speed = -0.6;
    public static final double shooter1_speed = 1.0;
    public static final double shooter2_speed = 1.0;
    //! Increment at which we ramp output from the x-axis
    public static final double x_ramp_increment = 0.1;
    //! Increment at which we ramp output from the y-axis
    public static final double y_ramp_increment = 0.1;
    //! Increment at which we ramp output from the z-axis
    public static final double z_ramp_increment = 0.1;
    //! Default drive mode - 'a' = arcade, 'm' = mecanum, 't' = tank, 'l' = locked
    public static final int default_drive_mode = 2;
    // Adjustment macros by Adam Bryant
    // Used in normalization functions

}