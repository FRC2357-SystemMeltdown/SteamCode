package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.oi.DriveOI;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReverseCommand extends Command {
	/**
	 * We would like to have {@link DriveOI} final and initialized during
	 * construction but the commands are typically created as part of DriveOI
	 * initialization so DriveOI may not be available as this command is
	 * constructed. So, initialization of this field is put off until
	 * {@link #initialize()}.
	 */
	protected /* final */ DriveOI driveOI;

	public ReverseCommand() {
	}

	/**
	 * {@inheritDoc}
	 */
	protected void initialize() {
		this.driveOI = Robot.getInstance().getOI().getDriveOI();
		driveOI.setReverse(true);
	}

	/**
	 * {@inheritDoc}
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void end() {
		driveOI.setReverse(false);
	}
}
