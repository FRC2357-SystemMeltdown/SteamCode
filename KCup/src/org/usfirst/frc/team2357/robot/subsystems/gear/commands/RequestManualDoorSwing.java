package org.usfirst.frc.team2357.robot.subsystems.gear.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.oi.GearOI;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command records in the {@link GearOI} that a manual door swing was
 * requested and moves us into manual mode if we are not already there.
 */
public class RequestManualDoorSwing extends Command {
	/**
	 * We would like to have {@link GearOI} final and initialized during
	 * construction but the commands are typically created as part of GearOI
	 * initialization so GearOI may not be available as this command is
	 * constructed. So, initialization of this field is put off until
	 * {@link #initialize()}.
	 */
	private /* final */ GearOI gearOI;

	public RequestManualDoorSwing() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		this.gearOI = Robot.getInstance().getOI().getGearOI();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void execute() {
		this.gearOI.requestManualDoorSwing();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}
}
