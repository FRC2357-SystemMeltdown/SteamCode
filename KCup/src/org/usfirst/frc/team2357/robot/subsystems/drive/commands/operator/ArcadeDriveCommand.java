package org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator;

import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

/**
 * One of the configurable default commands for the drive subsystem. See
 * {@link DriveSubsystem.DriveProperties} for configuration details.
 */
public class ArcadeDriveCommand extends AbstractDriveCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void execute() {
		this.driveSubsystem.arcadeDrive(this.driveOI.getArcadeDriveMoveValue(), this.driveOI.getArcadeDriveTurnValue());
	}
}
