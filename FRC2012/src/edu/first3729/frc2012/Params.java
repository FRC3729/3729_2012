/*  ______ ______ ______ ______
 * |__    |      |__    |  __  |
 * |__    |_     |    __|__    |
 * |______| |____|______|______|
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.camera.AxisCamera;

public class Params {
    // Port # constants: PWM out
    //! Port # of the front left Jaguar on the drive train

    public static final int fl_port = 1;
    //! Port # of the front right Jaguar on the drive train
    public static final int fr_port = 4;
    //! Port # of the back left Jaguar on the drive train
    public static final int bl_port = 2;
    //! Port # of the back right Jaguar on the drive train
    public static final int br_port = 6;
    public static final int shooter1_relay_port = 1;
    public static final int shooter2_relay_port = 2;
    public static final int intake_relay_port = 3;
    public static final int elevator_relay_port = 4;
    public static final int bridge_relay_port = 5;
    public static final int net_relay_port = 6;
    public static final int intake_limit_digin_port = 1;
    public static final double drive_creep_scale_factor = 0.2;
    public static final String camera_IP = "10.37.29.3";
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
    //! Increment at which we ramp output from the x-axis
    public static final double x_ramp_increment = 0.1;
    //! Increment at which we ramp output from the y-axis
    public static final double y_ramp_increment = 0.1;
    //! Increment at which we ramp output from the z-axis
    public static final double z_ramp_increment = 0.1;
    //! Default drive mode - 'a' = arcade, 'm' = mecanum, 't' = tank, 'l' = locked
    public static final char default_drive_mode = 'a';
    // Adjustment macros by Adam Bryant
    // Used in normalization functions
    //! Adjustment for the fact that the joystick is slightly off-center
    public static final double YCENTER = 0.03125;
    //! Adjustment for the fact that the joystick is slightly off-center
    public static final double ROTCENTER = 0.0156;
    //! Minimum possible X value
    public static final double XMIN = -0.641;
    //! Maximum possible X value
    public static final double XMAX = 0.648;
    //! Minimum possible Y value
    public static final double YMIN = (-0.57 - YCENTER);
    //! Maximum possilble Y value
    public static final double YMAX = (0.641 - YCENTER);
    //! Minimum possible Z value
    public static final double ZMIN = (-0.54);
    //! Maximum possible Z value
    public static final double ZMAX = (0.63);
    //! Minimum possible rotation value
    public static final double ROTMIN = (-0.64 - ROTCENTER);
    //! Maximum possible rotation value
    public static final double ROTMAX = (0.68 - ROTCENTER);
    //! Exponential constant for modifying input from the x-axis
    public static final double XEXPO = 0.4;
    //! Exponential constant for modfying input from the y-axis
    public static final double YEXPO = 0.4;
    //! Exponential constant for modifying input from the rotational axis
    public static final double ROTEXPO = 0.6;
    //! Exponential constant for modifying input from a joystick
    public static final double JOYEXPO = 0.5;
}