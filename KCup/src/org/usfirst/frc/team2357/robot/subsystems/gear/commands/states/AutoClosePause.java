package org.usfirst.frc.team2357.robot.subsystems.gear.commands.states;

/**
 * This command waits for the auto close backoff time or a manual door swing
 * request.
 */
public class AutoClosePause extends AbstractGearStateCommand {
	/**
	 * Sets the timeout from the subsystem configuration for the backoff time.
	 */
	public AutoClosePause() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		setTimeout(this.gearSubsystem.getAutoCloseBackoffTime());
	};

	@Override
	protected void execute() {
		this.gearSubsystem.openDoors();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Finished upon timeout or a manual door close is requested.
	 * </p>
	 */
	@Override
	protected boolean isFinished() {
		return isTimedOut() || this.gearOI.isManualDoorSwingRequested();
	}
}
