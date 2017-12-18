package org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.command.AbstractStateCommand;

/**
 * Used to drive the robot at arcade type move and turn settings.
 */
public abstract class AbstractDriveRobot extends AbstractStateCommand {
	protected final DriveSubsystem driveSubsystem;
	private double ms = 0.0;

	/**
	 * Drives until interrupted or drives for the specified milliseconds.
	 * 
	 * @param msTime
	 *            time in milliseconds for this command to drive (use -1.0 to
	 *            drive forever).
	 */
	public AbstractDriveRobot(double msTime) {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		requires(this.driveSubsystem);
		ms = msTime;
	}

	protected abstract double getNextMove();

	protected abstract double getNextTurn();

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Sets the timeout for stopping the robot if one was specified.
	 * </p>
	 */
	protected void initialize() {
		super.initialize();
		if (ms != -1.0) {
			setTimeout(ms / 1000.0);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Drives the robot at the specified move and turn.
	 * </p>
	 */
	protected void execute() {
		super.execute();
		this.driveSubsystem.arcadeDrive(-getNextMove(), -getNextTurn());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if timed out and false otherwise.
	 */
	protected boolean isFinished() {
		if (ms != -1.0) {
			return isTimedOut();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Stops the robot.
	 * </p>
	 */
	protected void end() {
		this.driveSubsystem.stop();
		super.end();
	}
}
