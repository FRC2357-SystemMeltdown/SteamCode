package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

/**
 * One of the configurable default commands for the drive subsystem. See
 * {@link DriveSubsystem.DriveProperties} for configuration details.
 */
public class TankDriveCommand extends AbstractDriveCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void execute() {
		this.driveSubsystem.tankDrive(this.driveOI.getTankLeft(), this.driveOI.getTankRight());
	}
}
