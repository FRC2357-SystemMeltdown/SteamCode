package org.usfirst.frc.team2357.robot.subsystems.gear.commands.states;

import edu.wpi.first.wpilibj.command.NextStateProvider;
import edu.wpi.first.wpilibj.command.State;

/**
 * This command keeps the doors open until a manual swing is requested or we are
 * auto and no longer pegged. This command also provides the next state (see
 * {@link #getNextState()}).
 */
public class IdleOpen extends AbstractGearStateCommand implements NextStateProvider {
	/**
	 * Creates a new instance of this command to keep the doors open.
	 */
	public IdleOpen() {
		super();
	}

	@Override
	protected void execute() {
		this.gearSubsystem.openDoors();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Finished if a manual swing is requested or auto and no longer pegged.
	 * </p>
	 */
	@Override
	protected boolean isFinished() {
		return this.gearOI.isManualDoorSwingRequested()
				|| (this.gearSubsystem.isAutoDoorsEnabled() && !this.gearSubsystem.isPegged());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * If manual, close the doors right away. If auto, pause first.
	 * </p>
	 */
	@Override
	public State getNextState() {
		State nextState = DoorsCommandState.IDLE_CLOSED;
		if (this.gearSubsystem.isAutoDoorsEnabled()) {
			nextState = DoorsCommandState.AUTO_CLOSE_PAUSE;
		}
		return nextState;
	}
}
