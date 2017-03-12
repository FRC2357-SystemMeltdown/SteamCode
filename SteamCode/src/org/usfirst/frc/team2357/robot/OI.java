package org.usfirst.frc.team2357.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2357.robot.commands.ClimbDown;
import org.usfirst.frc.team2357.robot.commands.ClimbUp;
import org.usfirst.frc.team2357.robot.commands.DriveDistance;
import org.usfirst.frc.team2357.robot.commands.DriveRobot;
import org.usfirst.frc.team2357.robot.commands.ManuallySwingDoor;
import org.usfirst.frc.team2357.robot.commands.PingAlignToFeeder;
import org.usfirst.frc.team2357.robot.commands.PingGearBinManual;
import org.usfirst.frc.team2357.robot.commands.SwitchGears;
import org.usfirst.frc.team2357.robot.commands.TurnToFixedAngle;
import org.usfirst.frc.team2357.robot.commands.TurnToVisionAngle;
import org.usfirst.frc.team2357.robot.commands.VisionTurnAndDrive;
import org.usfirst.frc.team2357.robot.triggers.ManuallySwingDoorTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.
	private XboxController driveController = new XboxController(0);
	private Button a = new JoystickButton(driveController, 1);
	private Button b = new JoystickButton(driveController, 2);
	private Button x = new JoystickButton(driveController, 3);
	private Button y = new JoystickButton(driveController, 4);
	private Button leftBumper = new JoystickButton(driveController, 5);
	private Button rightBumper = new JoystickButton(driveController, 6);
	private Button backButton = new JoystickButton(driveController, 7);
	private Button startButton = new JoystickButton(driveController, 8);
	private Button leftStickButton = new JoystickButton(driveController, 9);
	private Button rightStickButton = new JoystickButton(driveController, 10);
	
	private Joystick coDriveController = new Joystick(1);
	private Button coA = new JoystickButton(coDriveController, 2);
	private Button coB = new JoystickButton(coDriveController, 3);
	private Button coX = new JoystickButton(coDriveController, 1);
	private Button coY = new JoystickButton(coDriveController, 4);
	private Button coLeftBumper = new JoystickButton(coDriveController, 5);
	private Button coRightBumper = new JoystickButton(coDriveController, 6);
	private Button coStart = new JoystickButton(coDriveController, 10);
	private Button coSelect = new JoystickButton(coDriveController, 9);
	
	private ManuallySwingDoorTrigger doorTrigger = new ManuallySwingDoorTrigger(coB);
	
	public boolean reverse = false;
	
	public OI()
	{
		SmartDashboard.putNumber("TurnFixedAngle", 0.0);
		
		y.toggleWhenPressed(new SwitchGears());
		b.toggleWhenPressed(new ClimbUp());
		backButton.toggleWhenPressed(new ClimbDown());
		startButton.toggleWhenPressed(new TurnToVisionAngle());
		x.toggleWhenPressed(new TurnToFixedAngle(30.0));
		//coA.whenPressed(new DriveRobot(0.5, 0.0, 1000));
		//leftBumper.whenPressed(new DriveDistance(5.4));
		
		coStart.toggleWhenPressed(new PingGearBinManual());
		doorTrigger.whenActive(new ManuallySwingDoor());
		coSelect.whenPressed(new PingAlignToFeeder());
		leftBumper.whileHeld(new VisionTurnAndDrive());
		
		
	}

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	/**
	 * Get the XBox driver controller for the left side when using tank drive.
	 * @return the XBox driver controller left side value for tank drive
	 */
	public boolean getCoA()
	{
		return coA.get();
	}
	
	public double getTankLeft()
	{
		return driveController.getY(Hand.kLeft);
	}
	
	/**
	 * Get the XBox driver controller for the right side when using tank drive.
	 * @return the XBox driver controller right side value for tank drive
	 */
	public double getTankRight()
	{
		return driveController.getY(Hand.kRight);
	}
	
	/**
	 * Get the XBox driver controller value for the arcade drive move value.
	 * @return the XBox driver controller value for the arcade drive move value
	 */
	public double getArcadeDriveMoveValue()
	{
		if(reverse == false){
			return driveController.getY(Hand.kLeft);
		} else {
			return -driveController.getY(Hand.kLeft);
		}

	}
	
	/**
	 * Get the XBox driver controller value for the arcade drive turn value.
	 * @return the XBox driver controller value for the arcade drive turn value
	 */
	public double getArcadeDriveTurnValue()
	{
		return driveController.getX(Hand.kLeft);
	}
	
	public double getSplitArdcadeDriveTurnValue()
	{
		if(reverse == false){
			return driveController.getX(Hand.kRight);
		} else {
			return -driveController.getX(Hand.kRight);
		}
	}

}
