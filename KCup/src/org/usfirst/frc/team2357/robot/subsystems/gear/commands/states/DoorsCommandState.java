package org.usfirst.frc.team2357.robot.subsystems.gear.commands.states;

import org.usfirst.frc.team2357.robot.subsystems.gear.commands.DoorsStateMachineCommand;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateCommand;

/**
 * An enumeration of the command states for opening and closing the doors
 * (manual and auto) using {@link DoorsStateMachineCommand}. There is one state to hold
 * the doors closed, one to hold them open and a third used in automatic doors
 * mode that pauses between open and close after scoring a gear.
 */
public enum DoorsCommandState implements State {
	IDLE_OPEN(null, new IdleOpen()),
	IDLE_CLOSED(IDLE_OPEN, new IdleClosed()),
	AUTO_CLOSE_PAUSE(IDLE_CLOSED, new AutoClosePause());

	private final DoorsCommandState nextState;

	private final StateCommand commandToRun;

	/**
	 * Used to construct each enumeration value.
	 * 
	 * @param nextState
	 *            the next state or null if the command will chose it.
	 * @param commandToRun
	 *            the command to run in this state.
	 */
	private DoorsCommandState(DoorsCommandState nextState, StateCommand commandToRun) {
		this.nextState = nextState;
		this.commandToRun = commandToRun;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State getNextState() {
		return this.nextState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateCommand getCommandToRun() {
		return this.commandToRun;
	}
}
