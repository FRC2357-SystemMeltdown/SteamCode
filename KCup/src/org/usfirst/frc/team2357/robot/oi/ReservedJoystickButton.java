package org.usfirst.frc.team2357.robot.oi;

import java.util.logging.Level;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Overrides the low level {@link Trigger} methods that associates a
 * {@link Command} with this button to make sure no command gets associated.
 */
public class ReservedJoystickButton extends JoystickButton {
	/**
	 * Create a joystick button for triggering no command.
	 *
	 * @param joystick
	 *            The GenericHID object that has the button (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param buttonNumber
	 *            The button number (see {@link GenericHID#getRawButton(int) }
	 */
	public ReservedJoystickButton(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whenActive(Command command) {
		logNoCommand();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whileActive(Command command) {
		logNoCommand();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void whenInactive(Command command) {
		logNoCommand();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleWhenActive(Command command) {
		logNoCommand();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancelWhenActive(Command command) {
		logNoCommand();
	}

	/**
	 * Logs a severe error about {@link Command} association failure.
	 */
	private void logNoCommand() {
		IllegalStateException ise = new IllegalStateException(
				"Attempt made to associate a command with a button that cannot have a command associated.");
		Robot.getInstance().getLogger().log(Level.SEVERE, "Button to command association failed.", ise);
	}
}
