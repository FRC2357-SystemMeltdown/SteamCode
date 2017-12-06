package org.usfirst.frc.team2357.robot.oi;

import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.triggers.TwoButtonTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class and its siblings in the oi package form the glue that binds the
 * controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 */
public class OI {
	// Raw drive controller set up.
	final XboxController driveController = new XboxController(RobotMap.DRIVE_CONTROLLER_PORT);
	final Button driveA = new OneCmdJoystickButton(driveController, RobotMap.XBOX_A);
	final Button driveB = new OneCmdJoystickButton(driveController, RobotMap.XBOX_B);
	final Button driveX = new OneCmdJoystickButton(driveController, RobotMap.XBOX_X);
	final Button driveY = new OneCmdJoystickButton(driveController, RobotMap.XBOX_Y);
	final Button driveLeftBumper = new OneCmdJoystickButton(driveController, RobotMap.XBOX_LEFT_BUMPER);
	final Button driveRightBumper = new OneCmdJoystickButton(driveController, RobotMap.XBOX_RIGHT_BUMPER);
	final Button driveBackButton = new OneCmdJoystickButton(driveController, RobotMap.XBOX_BACK_BUTTON);
	final Button driveStartButton = new ReservedJoystickButton(driveController, RobotMap.XBOX_START_BUTTON);
	final Button driveLeftStickButton = new OneCmdJoystickButton(driveController, RobotMap.XBOX_LEFT_STICK_BUTTON);
	final Button driveRightStickButton = new OneCmdJoystickButton(driveController, RobotMap.XBOX_RIGHT_STICK_BUTTON);

	final Joystick coDriveController = new Joystick(RobotMap.CO_DRIVE_CONTROLLER_PORT);
	final Button coA = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_A);
	final Button coB = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_B);
	final Button coX = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_X);
	final Button coY = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_Y);
	final Button coLeftBumper = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_LEFT_BUMPER);
	final Button coRightBumper = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_RIGHT_BUMPER);
	final Button coStart = new ReservedJoystickButton(coDriveController, RobotMap.NINTENDO_START);
	final Button coSelect = new OneCmdJoystickButton(coDriveController, RobotMap.NINTENDO_SELECT);

	final Trigger configResetTrigger = new TwoButtonTrigger(this.driveStartButton, this.coStart);

	private final ConfigurationOI configurationOI;
	private final DriveOI driveOI;
	private final GearOI gearOI;
	private final VisionOI visionOI;

	/**
	 * Creates the subsystem OI bits.
	 */
	public OI() {
		this.configurationOI = new ConfigurationOI(this);
		this.driveOI = new DriveOI(this);
		this.gearOI = new GearOI(this);
		this.visionOI = new VisionOI(this);
	}

	/**
	 * @return the OI API class for the configuration subsystem.
	 */
	public ConfigurationOI getConfigurationOI() {
		return this.configurationOI;
	}

	/**
	 * @return the OI API class for the drive subsystem.
	 */
	public DriveOI getDriveOI() {
		return this.driveOI;
	}

	/**
	 * @return the OI API class for the gear subsystem.
	 */
	public GearOI getGearOI() {
		return this.gearOI;
	}

	/**
	 * @return the OI API class for the vision subsystem.
	 */
	public VisionOI getVisionOI() {
		return this.visionOI;
	}
}
