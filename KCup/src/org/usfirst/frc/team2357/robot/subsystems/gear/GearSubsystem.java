package org.usfirst.frc.team2357.robot.subsystems.gear;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;
import org.usfirst.frc.team2357.robot.subsystems.gear.commands.DoorsStateMachineCommand;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.DefaultStateCommandWrapper;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Used to open and close the doors of the gear bin.
 */
public class GearSubsystem extends Subsystem {
	private DoubleSolenoid doorsSolenoid = new DoubleSolenoid(RobotMap.CLOSE_GEAR_DOORS, RobotMap.OPEN_GEAR_DOORS);

	private DigitalInput pegSwitch1 = new DigitalInput(RobotMap.GEAR_PEG_SWITCH_1);
	private DigitalInput pegSwitch2 = new DigitalInput(RobotMap.GEAR_PEG_SWITCH_2);

	private DoorsPosition doorsPosition = DoorsPosition.CLOSED;
	private boolean autoDoorsEnabled = true;

	private GearProperties props = new GearProperties();

	/**
	 * An enum of the possible door positions.
	 */
	public enum DoorsPosition {
		CLOSED,
		OPEN;
	}

	/**
	 * Constructs the gear subsystem and registers it with the configuration
	 * subsystem. The properties will be initialized as a result.
	 */
	public GearSubsystem() {
		super();
		Robot.getInstance().getConfigurationSubsystem().addConsumer(this.props);
	}

	/**
	 * The only command that should EVER require the gear subsystem is its
	 * default command (directly or via its sub-commands ). It keeps running the
	 * entire match.
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultStateCommandWrapper(new DoorsStateMachineCommand(), this));
	}

	/**
	 * Returns the current door position which in turn can be asked if it is
	 * opened/closed and auto/manual.
	 * 
	 * @return the current {@link DoorsPosition}.
	 */
	public DoorsPosition getDoorsPosition() {
		return this.doorsPosition;
	}

	/**
	 * Opens the doors and sets the state.
	 */
	public void openDoors() {
		this.doorsSolenoid.set(DoubleSolenoid.Value.kReverse);
		this.doorsPosition = DoorsPosition.OPEN;
	}

	/**
	 * Closes the doors and sets the state.
	 */
	public void closeDoors() {
		this.doorsSolenoid.set(DoubleSolenoid.Value.kForward);
		this.doorsPosition = DoorsPosition.CLOSED;
	}

	public boolean isAutoDoorsEnabled() {
		return this.autoDoorsEnabled;
	}

	public void toggleAutoDoorsEnabled() {
		this.autoDoorsEnabled = !this.autoDoorsEnabled;
	}

	/**
	 * @return true if the peg has been detected by the peg switches.
	 */
	public boolean isPegged() {
		return (this.pegSwitch1.get() && this.pegSwitch2.get());
	}

	/**
	 * @return the time (in seconds) to wait to close the doors after scoring.
	 */
	public double getAutoCloseBackoffTime() {
		return props.autoCloseBackoffTime;
	}

	/**
	 * An instance of this class is used by the {@link GearSubsystem} to manage
	 * the gear handling properties using the {@link ConfigurationSubsystem}.
	 */
	public class GearProperties implements ConfigurationPropertiesConsumer {
		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 * The value is in seconds and will be used to delay closing the doors
		 * while backing off the peg.
		 */
		public static final String AUTO_CLOSE_BACKOFF_TIME_KEY = "gears.auto.close.backoff.time";
		public static final double AUTO_CLOSE_BACKOFF_TIME_DEFAULT = 1.5;
		private double autoCloseBackoffTime = AUTO_CLOSE_BACKOFF_TIME_DEFAULT;

		@Override
		public void reset(ConfigurationSubsystem config) {
			this.autoCloseBackoffTime = ConfigurationUtilities.getProperty(config, AUTO_CLOSE_BACKOFF_TIME_KEY,
					AUTO_CLOSE_BACKOFF_TIME_DEFAULT);
		}
	}
}
