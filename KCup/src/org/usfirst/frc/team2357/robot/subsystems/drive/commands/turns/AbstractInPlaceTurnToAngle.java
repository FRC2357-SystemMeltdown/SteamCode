package org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Implements most of in place angle turning except where the angle comes from.
 */
public abstract class AbstractInPlaceTurnToAngle extends Command {
	protected final DriveSubsystem driveSubsystem;
	private final double timeout;
	protected double targetAngle;

	public AbstractInPlaceTurnToAngle(double angle) {
		this(angle, 1.2);
	}

	public AbstractInPlaceTurnToAngle(double angle, double timeout) {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		requires(this.driveSubsystem);
		this.targetAngle = angle;
		this.timeout = timeout;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		this.driveSubsystem.startInPlaceTurnToAngle(targetAngle);
		if (this.timeout >= 0.0)
			setTimeout(this.timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		this.driveSubsystem.arcadeDrive(0.0, this.driveSubsystem.getInPlaceTurnRate());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.driveSubsystem.isInPlaceTurnOnTarget() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		this.driveSubsystem.stopInPlaceTurnToAngle();
	}
}
