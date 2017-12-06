package org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible;

/**
 *
 */
public class DriveRobotToVision extends AbstractDriveRobot {
	private final double move;

	/**
	 * Drives until interrupted or drives for the specified milliseconds.
	 * 
	 * @param moveValue
	 *            the arcade drive style move value.
	 * @param msTime
	 *            time in milliseconds for this command to drive (use -1.0 to
	 *            drive forever).
	 */
	public DriveRobotToVision(double moveValue, double msTime) {
		super(msTime);
		this.move = moveValue;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		super.initialize();
		this.driveSubsystem.startStraightToVision();
	}

	@Override
	protected void end() {
		this.driveSubsystem.stopStraightToVision();
		super.end();
	}

	@Override
	protected double getNextMove() {
		return this.move;
	}

	@Override
	protected double getNextTurn() {
		if (this.driveSubsystem.isStraightToVisionTurnOnTarget()) {
			this.driveSubsystem.startStraightToVision();
		}
		return this.driveSubsystem.getStraightToVisionTurnRate();
	}
}
