package org.usfirst.frc.team2357.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1; 
	// public static int rangefinderModule = 1;
	public static final int leftDrive1 = 1;
	public static final int leftDrive2 = 3;
	public static final int rightDrive1 = 2;
	public static final int rightDrive2 = 4;
	
	public static final int leftGearServo = 1;
	public static final int rightGearServo = 0;
	
	public static final int leftWinchController = 2;
	public static final int rightWinchController = 3;
	
	public static final int imgHeight = 240;
	public static final int imgWidth = 320;

	public static final double PIDp = 0.025; // 0.03
	public static final double PIDi = 0.002; // 0.001
	public static final double PIDd = 0.0; // 0.0004
	public static final double PIDtol = 2.0;
	
	public static final double cameraFOV = 41;
	
}
