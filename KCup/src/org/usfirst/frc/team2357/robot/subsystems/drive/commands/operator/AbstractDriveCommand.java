package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.oi.DriveOI;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A base class for the configurable default commands for the drive subsystem.
 * See {@link DriveSubsystem.DriveProperties} for configuration details.
 */
public abstract class AbstractDriveCommand extends Command {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	protected final DriveSubsystem driveSubsystem;

	/**
	 * We would like to have {@link DriveOI} final and initialized during
	 * construction but the commands are typically created as part of DriveOI
	 * initialization so DriveOI may not be available as this command is
	 * constructed. So, initialization of this field is put off until
	 * {@link #initialize()}.
	 */
	protected /* final */ DriveOI driveOI;

	/**
	 * Called to create the {@link DriveSubsystem} default command if so
	 * configured.
	 */
	public AbstractDriveCommand() {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		requires(this.driveSubsystem);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		this.driveOI = Robot.getInstance().getOI().getDriveOI();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}
}
