package org.usfirst.frc.team2357.robot.subsystems;

//import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;

//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;
//import edu.wpi.first.wpilibj.vision.*;

/**
 *
 */



public class VisionSub extends Subsystem {
    
	private VisionThread visionThread;
	
	private final Object imgLock = new Object();
	private double centerX = 0.0;
	public UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        
        
        public VisionSub()
        {
        	camera.setResolution(RobotMap.imgWidth, RobotMap.imgHeight);
            camera.setExposureManual(25);

            visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
                if (!pipeline.findContoursOutput().isEmpty()) {
                    Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
                    synchronized (imgLock) {
                        centerX = r.x + (r.width / 2);
                    }
                }
            });
            visionThread.start();
            
        }       
        

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

