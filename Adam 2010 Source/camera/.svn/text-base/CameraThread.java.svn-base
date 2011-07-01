/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author adam
 */
public class CameraThread extends Thread
{

    private double kScoreThreshold = .01;
    private AxisCamera cam;
    private Target[] targets;
    private boolean targetFound = false;
    private boolean active = false;

    public CameraThread()
    {
        // Setup camera
        cam = AxisCamera.getInstance();
        cam.writeResolution(AxisCamera.ResolutionT.k320x240);
        cam.writeCompression(20);
        cam.writeBrightness(0);
    }

    public void run()
    {
        while (true)
        {
            if (active)
            {
                if (cam.freshImage())
                {
                    ColorImage image;
                    try
                    {
                        image = cam.getImage();

                        Thread.yield(); 
                        targets = Target.findCircularTargets(image);
                        Thread.yield();
                        image.free();
                        if (targets.length == 0 || targets[0].m_score < kScoreThreshold)
                        {
                            this.targetFound = false;
                        }
                        else
                        {
                            this.targetFound = true;
                        }
                    }
                    catch (AxisCameraException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (NIVisionException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                targetFound = false;

                try
                {
                    Thread.sleep(5);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

            Thread.yield();
        }
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }

    public boolean isTargetFound()
    {
        return targetFound;
    }

    public Target[] getTargets()
    {
        return targets;
    }
}
