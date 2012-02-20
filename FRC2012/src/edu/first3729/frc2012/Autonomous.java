/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.first3729.frc2012;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;

/**
 *
 * @author matthewhaney
 */
public class Autonomous {
    
    private Drive _drive;
    private AxisCamera _camera;
    private Manipulator _manip;
    private BinaryImage _image;
    
    public Autonomous(Drive drv, AxisCamera cam, Manipulator manip)
    {
        
    }
    
    public void init()
    {
        
    }
    
    public void run()
    {/*
        try {
        this._image = this._camera.getImage().thresholdHSL(Params.target_hue_low, Params.target_hue_high, Params.target_saturation_low, Params.target_saturation_high, Params.target_luminence_low, Params.target_luminence_high);
        
        }
        catch (AxisCameraException ex) {
            
        }
        catch (NIVisionException ex) {
            
        }
        * */
    }
}
