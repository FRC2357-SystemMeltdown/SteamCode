package org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible;

/**
 * Used to drive the robot at fixed arcade type move and turn settings.
 */
public class DriveRobot extends AbstractDriveRobot {
	private double move = 0.0;
	private double turn = 0.0;

	/**
	 * Drives until interrupted or drives for the specified milliseconds.
	 * 
	 * @param moveValue
	 *            the arcade drive style move value.
	 * @param turnValue
	 *            the arcade drive style turn value.
	 * @param msTime
	 *            time in milliseconds for this command to drive (use -1.0 to
	 *            drive forever).
	 */
	public DriveRobot(double moveValue, double turnValue, double msTime) {
		super(msTime);
		this.move = moveValue;
		this.turn = turnValue;
	}

	@Override
	protected double getNextMove() {
		return this.move;
	}

	@Override
	protected double getNextTurn() {
		return this.turn;
	}
}
