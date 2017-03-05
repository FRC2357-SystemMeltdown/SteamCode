package org.usfirst.frc.team2357.robot.subsystems;

import java.util.List;

import javax.swing.plaf.metal.MetalFileChooserUI.FilterComboBoxRenderer;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	private boolean alignedToFeeder = false;
	
	public double turnAng;
	
	private int threadMiscount = 0;
	
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
            	try {
            	List<MatOfPoint> contours = pipeline.filterContoursOutput();
                if (!contours.isEmpty() && contours.size() >= 2) {
                	threadMiscount = 0;
                	MatOfPoint contour1 = contours.get(0);
                	MatOfPoint contour2 = contours.get(1);
                	for (int i = 2; i < contours.size(); i++) {
                		MatOfPoint nextContour = contours.get(i);                		
                		if(nextContour.elemSize() > contour1.elemSize()){
                			contour1 = nextContour;
                		} else if(nextContour.elemSize() > contour2.elemSize()){
                			contour2 = nextContour;
                		}
                	}

                    Rect r1 = Imgproc.boundingRect(contour1);
                    Rect r2 = Imgproc.boundingRect(contour2);
                    synchronized (imgLock) {
                        centerX = (((r1.x + (r1.width / 2)) + ((r2.x + r2.width) - (r2.width / 2))) / 2);
                        double turnRatio = (centerX / (RobotMap.imgWidth/2)) - 1;
                    	turnAng = turnRatio * RobotMap.cameraFOVConst;
                    }
                    
                } else if((!contours.isEmpty() && contours.size() == 1)){
                	threadMiscount = 0;
                	MatOfPoint contour1 = contours.get(0);
                	for (int i = 2; i < contours.size(); i++) {
                		MatOfPoint nextContour = contours.get(i);                		
                		if(nextContour.elemSize() > contour1.elemSize()){
                			contour1 = nextContour;
                		}
                	}
                    Rect r1 = Imgproc.boundingRect(contour1);
                    synchronized (imgLock) {
                        centerX = r1.x + (r1.width / 2);
                        double turnRatio = (centerX / (RobotMap.imgWidth/2)) - 1;
                    	turnAng = turnRatio * RobotMap.cameraFOVConst;
                    }
                } else if(threadMiscount > 3){
                	turnAng = 0.0;
                }else{
                	threadMiscount ++;
                }
                SmartDashboard.putNumber("TurnAng", turnAng);
					VisionThread.sleep(100);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					System.out.println("Error 1");
					e.printStackTrace();
				}
            });
            visionThread.start();    
        }
        
        public double getCenterX() {
			return centerX;
		}
        
        public Object getImgLock() {
			return imgLock;
		}
        
        public double getTurnAng() {
			return turnAng;
		}
        
        public boolean isAlignedToFeeder() {
			return alignedToFeeder;
		}

		public void setAlignedToFeeder(boolean alignedToFeeder) {
			this.alignedToFeeder = alignedToFeeder;
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

