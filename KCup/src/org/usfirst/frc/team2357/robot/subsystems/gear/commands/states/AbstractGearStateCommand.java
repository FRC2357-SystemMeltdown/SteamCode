package org.usfirst.frc.team2357.robot.subsystems.gear.commands.states;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.oi.GearOI;
import org.usfirst.frc.team2357.robot.subsystems.gear.GearSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.gear.commands.DoorsStateMachineCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.StateCommand;

/**
 * Base class for all {@link Command} instances run as part of
 * {@link DoorsStateMachineCommand}.
 */
public abstract class AbstractGearStateCommand extends StateCommand {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	protected final GearSubsystem gearSubsystem;

	/**
	 * We would like to have {@link GearOI} final and initialized during
	 * construction but the commands are typically created as part of GearOI
	 * initialization so GearOI may not be available as this command is
	 * constructed. So, initialization of this field is put off until
	 * {@link #initialize()}.
	 */
	protected /* final */ GearOI gearOI;

	/**
	 * Requires the gear subsystem as the sub-classes are the sub-commands of
	 * the {@link DoorsStateMachineCommand} which is the default command for the gears
	 * subsystem.
	 */
	public AbstractGearStateCommand() {
		this.gearSubsystem = Robot.getInstance().getGearSubsystem();
		requires(gearSubsystem);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize() {
		this.gearOI = Robot.getInstance().getOI().getGearOI();
	}
}
