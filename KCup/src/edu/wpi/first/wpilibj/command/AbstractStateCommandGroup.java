package edu.wpi.first.wpilibj.command;

/**
 * An abstract base class for all {@link CommandGroup}s that will be used as the
 * command for a {@link State} as part of a {@link StateMachineCommand}.
 * 
 * @author Steven Wasleski
 */
public abstract class AbstractStateCommandGroup extends CommandGroup implements StateCommand {
	private StateMachineCommand<?> stateMachine;

	public AbstractStateCommandGroup() {
		super();
	}

	/**
	 * Used by the containing {@link StateMachineCommand} to set itself for
	 * callbacks.
	 * 
	 * @param stateMachine
	 *            the containing {@link StateMachineCommand}.
	 */
	public void setStateMachine(StateMachineCommand<?> stateMachine) {
		this.stateMachine = stateMachine;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Upon the normal completion of this command, the next state, if any, is
	 * started.
	 * </p>
	 */
	@Override
	protected void end() {
		super.end();
		// In case this command is use outside a state machine check for null.
		if (this.stateMachine != null) {
			this.stateMachine.startNextState();
			this.stateMachine = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * If the current command is interrupted, the whole state machine ends.
	 * </p>
	 */
	@Override
	protected void interrupted() {
		// In case this command is use outside a state machine check for null.
		if (this.stateMachine != null) {
			this.stateMachine.terminate();
		}
		super.interrupted();
	}
}
