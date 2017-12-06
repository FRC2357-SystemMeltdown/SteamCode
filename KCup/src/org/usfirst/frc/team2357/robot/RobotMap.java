package org.usfirst.frc.team2357.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// OI controller port and button constants.
	public static final int DRIVE_CONTROLLER_PORT = 0;
	public static final int CO_DRIVE_CONTROLLER_PORT = 1;

	// XBox controller raw button map.
	public static final int XBOX_A = 1;
	public static final int XBOX_B = 2;
	public static final int XBOX_X = 3;
	public static final int XBOX_Y = 4;
	public static final int XBOX_LEFT_BUMPER = 5;
	public static final int XBOX_RIGHT_BUMPER = 6;
	public static final int XBOX_BACK_BUTTON = 7;
	public static final int XBOX_START_BUTTON = 8;
	public static final int XBOX_LEFT_STICK_BUTTON = 9;
	public static final int XBOX_RIGHT_STICK_BUTTON = 10;

	// Nintendo Gamepad raw button map.
	public static final int NINTENDO_A = 2;
	public static final int NINTENDO_B = 3;
	public static final int NINTENDO_X = 1;
	public static final int NINTENDO_Y = 4;
	public static final int NINTENDO_LEFT_BUMPER = 5;
	public static final int NINTENDO_RIGHT_BUMPER = 6;
	public static final int NINTENDO_START = 10;
	public static final int NINTENDO_SELECT = 9;

	// The CAN bus device numbers for the drive motor controllers.
	public static final int LEFT_DRIVE_1 = 1;
	public static final int LEFT_DRIVE_2 = 3;
	public static final int RIGHT_DRIVE_1 = 2;
	public static final int RIGHT_DRIVE_2 = 4;

	// Solenoid channels for gear bin doors.
	public static final int CLOSE_GEAR_DOORS = 6;
	public static final int OPEN_GEAR_DOORS = 7;

	// Gear subsystem peg detection switch ports. These are DIO ports.
	public static final int GEAR_PEG_SWITCH_1 = 0;
	public static final int GEAR_PEG_SWITCH_2 = 1;

	// The analog gyro channel must be 0 or 1.
	public static final int GYRO_ANALOG_CHANNEL = 1;
}
