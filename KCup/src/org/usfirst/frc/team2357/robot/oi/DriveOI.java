package org.usfirst.frc.team2357.robot.oi;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.ForceManualDrive;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ReverseCommand;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class DriveOI {
	private final OI oi;
	private boolean reverse = false;

	/**
	 * Note that we have to get the {@link OI} as a parameter rather than from
	 * the robot since the subsystem OI objects are created during {@link OI}
	 * construction and the {@link Robot} will not yet have the reference.
	 * 
	 * @param oi
	 *            the one and only OI instance for the robot.
	 */
	public DriveOI(OI oi) {
		this.oi = oi;
		this.oi.driveRightBumper.whileHeld(new ForceManualDrive());
		this.oi.driveLeftBumper.whenPressed(new ScoreGearStateMachineCommand());
		this.oi.driveBackButton.toggleWhenPressed(new ReverseCommand());
	}

	/**
	 * Return true if driving is currently reversed.
	 * 
	 * @return true if reversed; otherwise, false.
	 */
	public boolean isReverse() {
		return reverse;
	}

	/**
	 * Sets the drive to reverse (true) or normal (false).
	 * 
	 * @param reverse
	 *            true to set to reverse or false otherwise.
	 */
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	/**
	 * @return the left side drive value for tank drive.
	 */
	public double getTankLeft() {
		return Math.pow(this.oi.driveController.getY(Hand.kLeft), 3.0);
	}

	/**
	 * @return the right side drive value for tank drive.
	 */
	public double getTankRight() {
		return Math.pow(this.oi.driveController.getY(Hand.kRight), 3.0);
	}

	/**
	 * @return the move value for arcade drive.
	 */
	public double getArcadeDriveMoveValue() {
		double value = reverse ? -this.oi.driveController.getY(Hand.kLeft) : this.oi.driveController.getY(Hand.kLeft);
		return Math.pow(value, 3.0);
	}

	/**
	 * @return the turn value for arcade drive (same stick arcade).
	 */
	public double getArcadeDriveTurnValue() {
		return Math.pow(this.oi.driveController.getX(Hand.kLeft), 3.0);
	}

	/**
	 * @return the turn value for split arcade drive (two stick arcade).
	 */
	public double getSplitArcadeDriveTurnValue() {
		return Math.pow(this.oi.driveController.getX(Hand.kRight), 3.0);
	}
}
