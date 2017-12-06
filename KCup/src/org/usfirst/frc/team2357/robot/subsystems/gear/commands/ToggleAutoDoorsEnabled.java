package org.usfirst.frc.team2357.robot.subsystems.gear.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.gear.GearSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command forces the subsystem back to automatic doors mode.
 */
public class ToggleAutoDoorsEnabled extends Command {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	protected final GearSubsystem gearSubsystem;

	public ToggleAutoDoorsEnabled() {
		this.gearSubsystem = Robot.getInstance().getGearSubsystem();
		// Do not require the subsystem.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void execute() {
		this.gearSubsystem.toggleAutoDoorsEnabled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isFinished() {
		return true;
	}
}
