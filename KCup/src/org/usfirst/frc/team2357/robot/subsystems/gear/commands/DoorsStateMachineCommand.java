package org.usfirst.frc.team2357.robot.subsystems.gear.commands;

import org.usfirst.frc.team2357.robot.subsystems.gear.commands.states.DoorsCommandState;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateMachineCommand;

/**
 * A state command that runs the entire match and controls door movement
 * (automatic or manual).
 */
public class DoorsStateMachineCommand extends StateMachineCommand<DoorsCommandState> {

	/**
	 * Constructs the state command specifying the {@link DoorsCommandState} as
	 * the enum for the state machine.
	 */
	public DoorsStateMachineCommand() {
		super(DoorsCommandState.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * The initial state keeps the doors closed.
	 * </p>
	 */
	@Override
	protected State getInitialState() {
		return DoorsCommandState.IDLE_CLOSED;
	}
}
