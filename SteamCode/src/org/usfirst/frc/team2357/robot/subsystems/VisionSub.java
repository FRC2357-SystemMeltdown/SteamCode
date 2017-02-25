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
	private static final double imgCenter = 111.0;
	private double centerX = 0.0;
	public double turnOutput;
	private double turnRatio;
	public double turnAng;
	
	//private VideoCapture videoCapture = new VideoCapture(0);
        
        
        public VisionSub()
        {
        	UsbCamera visionCamera = CameraServer.getInstance().startAutomaticCapture(0);
        	//UsbCamera rearCamera = CameraServer.getInstance().startAutomaticCapture(1);
        	/*UsbCamera rearCamera = CameraServer.getInstance().startAutomaticCapture("rearCamera", 1);
        	rearCamera.setFPS(20);
        	rearCamera.setResolution(RobotMap.imgWidth, RobotMap.imgHeight);*/
        	
        	visionCamera.setFPS(20);
        	visionCamera.setResolution(RobotMap.imgWidth, RobotMap.imgHeight);
            visionCamera.setExposureManual(25);
            visionCamera.setBrightness(25);
            visionCamera.setWhiteBalanceManual(4500);
            
            
        	//camera.setExposureHoldCurrent();
        	//camera.setWhiteBalanceHoldCurrent();
        	
            
            visionThread = new VisionThread(visionCamera, new GripPipeline(), pipeline -> {
                if (!pipeline.filterContoursOutput().isEmpty() && pipeline.filterContoursOutput().size() >= 2) {
                    Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                    Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                    synchronized (imgLock) {
                        centerX = (((r1.x + (r1.width / 2)) + ((r2.x + r2.width) - (r2.width / 2))) / 2);
                        //System.out.println("vscenterX: " + centerX + " vsRect1X: " + r1.x + " vsRect1Width: " + r1.width);
                        //System.out.println("True center:" + centerX);
                        turnRatio = (centerX / imgCenter) - 1;
                    	turnAng = turnRatio * RobotMap.cameraFOV;
                    }                       
                   
                }
                try {
					VisionThread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Error 1");
					e.printStackTrace();
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
        
        public double getTurnAng() {
			return turnAng;
		}
        
        public void startVisionThread()
        {
        	visionThread.start();
        }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

