package org.usfirst.frc.team2357.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2357.robot.commands.ClimbDown;
import org.usfirst.frc.team2357.robot.commands.ClimbUp;
import org.usfirst.frc.team2357.robot.commands.DispenseGear;
import org.usfirst.frc.team2357.robot.commands.DriveDistance;
import org.usfirst.frc.team2357.robot.commands.DriveToTarget;
import org.usfirst.frc.team2357.robot.commands.SwitchGears;
import org.usfirst.frc.team2357.robot.commands.TurnToFixedAngle;
import org.usfirst.frc.team2357.robot.commands.TurnToVisionAngle;
import org.usfirst.frc.team2357.robot.triggers.PressurePlateTrigger;

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
	
	private PressurePlateTrigger pressurePlateTrigger = new PressurePlateTrigger();
	
	public OI()
	{
		y.toggleWhenPressed(new SwitchGears());
		b.toggleWhenPressed(new ClimbUp());
		backButton.toggleWhenPressed(new ClimbDown());
		startButton.whenPressed(new TurnToVisionAngle());
		pressurePlateTrigger.whenActive(new DispenseGear());
		
		
		//x.toggleWhenPressed(new DriveToTarget());
		//x.whileHeld(new driveToTarget());
		
		a.whileHeld(new DispenseGear());
		leftBumper.whenPressed(new DriveDistance(5.4));
		
		
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
		return driveController.getY(Hand.kLeft);
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
		return driveController.getX(Hand.kRight);
	}

}
