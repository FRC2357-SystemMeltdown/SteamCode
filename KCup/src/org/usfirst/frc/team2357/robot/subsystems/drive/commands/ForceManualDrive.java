package org.usfirst.frc.team2357.robot.subsystems.drive.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Used to stop automated drive commands and go back to manual drive.
 */
public class ForceManualDrive extends Command {
	private final DriveSubsystem driveSubsystem;

	/**
	 * Requires the drive subsystem to force out other commands.
	 */
	public ForceManualDrive() {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		this.requires(driveSubsystem);
	}

	/**
	 * {@inheritDoc}
	 */
	protected boolean isFinished() {
		return true;
	}
}
