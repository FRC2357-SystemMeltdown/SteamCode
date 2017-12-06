package org.usfirst.frc.team2357.robot.subsystems.gear.commands.states;

/**
 * This command keeps the doors closed until a manual swing is requested or we
 * are auto and get pegged.
 */
public class IdleClosed extends AbstractGearStateCommand {
	/**
	 * Creates a new instance of this command to keep the doors closed.
	 */
	public IdleClosed() {
		super();
	}

	@Override
	protected void execute() {
		this.gearSubsystem.closeDoors();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Finished if a manual swing is requested or auto and pegged.
	 * </p>
	 */
	@Override
	protected boolean isFinished() {
		return this.gearOI.isManualDoorSwingRequested()
				|| (this.gearSubsystem.isAutoDoorsEnabled() && this.gearSubsystem.isPegged());
	}
}
