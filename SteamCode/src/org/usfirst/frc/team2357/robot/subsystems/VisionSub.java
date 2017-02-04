package org.usfirst.frc.team2357.robot.subsystems;

import org.opencv.core.Mat;
//import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;

import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.PIDCommand;
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
	public double turnOutput;
	
	//private VideoCapture videoCapture = new VideoCapture(0);
        
        
        public VisionSub()
        {
        	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        	camera.setResolution(RobotMap.imgWidth, RobotMap.imgHeight);
            camera.setExposureManual(25);
            camera.setBrightness(25);
            camera.setWhiteBalanceManual(4500);
        	//camera.setExposureHoldCurrent();
        	//camera.setWhiteBalanceHoldCurrent();
        	
            
            visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
                if (!pipeline.filterContoursOutput().isEmpty()) {
                    Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                    synchronized (imgLock) {
                        centerX = r.x + (r.width / 2);
                        System.out.println("vscenterX: " + centerX + " vsRectX: " + r.x + " vsRectWidth: " + r.width);
                    }                       
                    /*try {
						visionThread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
                }
            	
            	/*Mat source = new Mat();
            	Mat output = new Mat();
            	
            	CvSink cvSink = CameraServer.getInstance().getVideo();          	
            	CvSource outputStream = CameraServer.getInstance().putVideo("Processed", RobotMap.imgWidth, RobotMap.imgHeight);
            	while(!VisionThread.interrupted())
            	{
            		cvSink.grabFrame(source);
                    //outputStream.putFrame(output);
            		//outputStream.putFrame(source);
            		
            		outputStream.putFrame(pipeline.rgbThresholdOutput());
            	}*/
                               
            });
            visionThread.start();
            
            /*new Thread(() -> {
            	GripPipeline pipeline = new GripPipeline();
            	
                pipeline.process(videoCapture.grab());
                //VideoCapture
            	
            }).start();*/
            
        }
        
        public double getCenterX() {
			return centerX;
		}
        
        /*public void centerOnTarget()
        {
        	//double centerX;
        	synchronized (imgLock) {
        		//centerX = this.centerX;
        	}
        	turnOutput = this.centerX - (RobotMap.imgWidth / 2);
        	
        }*/
        
        public Object getImgLock() {
			return imgLock;
		}
        
        public double getTurnOutput() {
			return turnOutput;
			//TODO make command run drive method and turn to (-0.6, turn * 0.005)
			//See Screensteps on utilizing GRIP generated code
		}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

