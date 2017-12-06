package org.usfirst.frc.team2357.robot.triggers;

import java.util.logging.Level;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Overrides the low level {@link Trigger} methods that associates a
 * {@link Command} with this trigger to make sure only one command gets
 * associated.
 */
public abstract class OneCmdTrigger extends Trigger {
	private boolean hasCommand = false;

	public OneCmdTrigger() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whenActive(Command command) {
		if (ensureOneCommand())
			super.whenActive(command);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whileActive(Command command) {
		if (ensureOneCommand())
			super.whileActive(command);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whenInactive(Command command) {
		if (ensureOneCommand())
			super.whenInactive(command);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleWhenActive(Command command) {
		if (ensureOneCommand())
			super.toggleWhenActive(command);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancelWhenActive(Command command) {
		if (ensureOneCommand())
			super.cancelWhenActive(command);
	}

	/**
	 * Returns true if this is the first {@link Command} to be associated with
	 * this trigger and association can continue. If false is returned, do not
	 * associate the command (a severe error has been logged).
	 * 
	 * @return true if it is okay to associate the command and false if not.
	 */
	private boolean ensureOneCommand() {
		boolean canAssociate = !this.hasCommand;
		if (this.hasCommand) {
			IllegalStateException ise = new IllegalStateException(
					"Attempt made to associate a command with a trigger that already has a command associated.");
			Robot.getInstance().getLogger().log(Level.SEVERE, "Trigger to command association failed.", ise);
		} else {
			this.hasCommand = true;
		}
		return canAssociate;
	}
}
