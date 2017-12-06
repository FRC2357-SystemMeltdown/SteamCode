package org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible;

import org.usfirst.frc.team2357.robot.Robot;

/**
 *
 */
public class ForeverDriveToPeg extends DriveRobot {

	public ForeverDriveToPeg(double driveVal, double rotateVal) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(driveVal, rotateVal, -1.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.getInstance().getGearSubsystem().isPegged();
	}
}
