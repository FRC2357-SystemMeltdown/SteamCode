package org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Implements most of smooth turn to angle, that is, a turn of a given radius at
 * a given speed.
 */
public abstract class AbstractRadiusTurnToAngle extends Command {
	protected final DriveSubsystem driveSubsystem;
	private final double timeout;
	protected double targetAngle;
	private final double radius;
	private final double move;
	private double curve;

	/**
	 * Turns to a given angle in a turn of the given radius. Provide a positive
	 * angle for a right turn and a negative angle for a left turn.
	 * 
	 * @param angle
	 *            the desired angle in degrees from current position. Provide a
	 *            positive angle for a turn to the right and a negative angle
	 *            for a turn to the left.
	 * @param radius
	 *            the radius of the turn in inches.
	 * @param move
	 *            the desired speed of the turn (-1.0 to 1.0) either backward or
	 *            forward.
	 * @param timeout
	 *            a timeout for the turn if the angle is not reached. Provide
	 *            -1.0 to not have a timeout.
	 */
	public AbstractRadiusTurnToAngle(double angle, double radius, double move, double timeout) {
		this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
		requires(this.driveSubsystem);
		this.targetAngle = angle;
		this.timeout = timeout;
		this.radius = radius;
		this.move = move;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		curve = this.driveSubsystem.startRadiusTurnToAngle(targetAngle, radius, move >= 0);
		if (this.timeout >= 0.0)
			setTimeout(this.timeout);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		this.driveSubsystem.radiusTurnDrive(move, curve);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.driveSubsystem.isRadiusTurnOnTarget() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		this.driveSubsystem.stopRadiusTurnToAngle();
	}
}
