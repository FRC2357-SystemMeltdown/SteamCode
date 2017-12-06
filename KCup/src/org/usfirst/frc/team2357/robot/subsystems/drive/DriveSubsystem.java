package org.usfirst.frc.team2357.robot.subsystems.drive;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.ArcadeDriveCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.SplitArcadeDriveCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.operator.TankDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link DriveSubsystem} is used by commands to drive the robot during both
 * autonomous and teleop. It owns all the drive motor controllers and drive
 * feedback sensors.
 */
public class DriveSubsystem extends Subsystem implements PIDOutput, PIDSource {
	private final CANTalon leftDrive = new CANTalon(RobotMap.LEFT_DRIVE_1);
	private final CANTalon rightDrive = new CANTalon(RobotMap.RIGHT_DRIVE_1);
	private final CANTalon leftDriveSlave = new CANTalon(RobotMap.LEFT_DRIVE_2);
	private final CANTalon rightDriveSlave = new CANTalon(RobotMap.RIGHT_DRIVE_2);
	private final RobotDrive robotDrive = new RobotDrive(this.leftDrive, this.rightDrive);

	private final AnalogGyro gyro = new AnalogGyro(RobotMap.GYRO_ANALOG_CHANNEL);
	private final PIDController turnController;

	private double turnRate;
	private int radiusTurnOvershoot = 0;

	private final DriveProperties props = new DriveProperties();

	/**
	 * Registers with the configuration subsystem (drive properties set at this
	 * time), sets up the turn controller (this subsystem acting as a PID source
	 * and output). It also initializes the drive controllers.
	 */
	public DriveSubsystem() {
		super();
		Robot.getInstance().getConfigurationSubsystem().addConsumer(this.props);

		// TODO more properties work to do here.
		turnController = new PIDController(props.turnPIDp, props.turnPIDi, props.turnPIDd, 0.0, this, this, 0.01);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(props.turnPIDTolerance);
		turnController.setContinuous(true);

		this.leftDrive.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.leftDrive.configEncoderCodesPerRev(128);
		this.leftDrive.setControlMode(0);
		leftDrive.setAllowableClosedLoopErr(50);
		this.leftDrive.setPID(0.5, 0.0005, 0.0);
		this.rightDrive.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		this.rightDrive.configEncoderCodesPerRev(128);
		this.rightDrive.setControlMode(0);
		rightDrive.setAllowableClosedLoopErr(50);
		this.rightDrive.setPID(0.5, 0.0005, 0.0);

		leftDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftDriveSlave.set(RobotMap.LEFT_DRIVE_1);
		rightDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightDriveSlave.set(RobotMap.RIGHT_DRIVE_1);
		robotDrive.setSafetyEnabled(false);
	}

	/**
	 * The operator drive control mode is configurable. See
	 * {@link DriveProperties#DRIVE_MODE_KEY} for details.
	 */
	public void initDefaultCommand() {
		if (this.props.driveMode == 0) {
			setDefaultCommand(new ArcadeDriveCommand());
		} else if (this.props.driveMode == 1) {
			setDefaultCommand(new TankDriveCommand());
		} else {
			setDefaultCommand(new SplitArcadeDriveCommand());
		}
	}

	/**
	 * Used to stop driving. Usually from command end methods.
	 */
	public void stop() {
		this.arcadeDrive(0.0, 0.0);
	}

	/* START CONTROLLER DRIVE FUNCTIONS */

	public void arcadeDrive(double moveValue, double rotateValue) {
		robotDrive.arcadeDrive(-moveValue, -rotateValue, false);
	}

	public void tankDrive(double leftValue, double rightValue) {
		robotDrive.tankDrive(leftValue, rightValue, false);
	}

	/* END CONTROLLER DRIVE FUNCTIONS */

	/* START TURN IN PLACE FUNCTIONS */

	public void startInPlaceTurnToAngle(double angle) {
		turnToAngle(angle, true);
	}

	public double getInPlaceTurnRate() {
		return getTurnRate();
	}

	public boolean isInPlaceTurnOnTarget() {
		return turnIsOnTarget();
	}

	public void stopInPlaceTurnToAngle() {
		stopTurnToAngle();
	}

	/* END TURN IN PLACE FUNCTIONS */

	/* START RADIUS TURN FUNCTIONS */

	/**
	 * Enables turning to the specified angle and then calculates and returns
	 * the curve value required to get us there at the specified radius turn.
	 * See {@link RobotDrive#drive(double, double)} for curve value details.
	 * 
	 * <p>
	 * Note that a radius turn does not use the output of the turn PID loop. It
	 * only checks for on target.
	 * </p>
	 * 
	 * @param angle
	 *            the desired angle in degrees from current position. Provide a
	 *            positive angle for a turn to the right and a negative angle
	 *            for a turn to the left.
	 * @param radius
	 *            the radius of the turn in inches.
	 * @param forward
	 *            true if the general robot movement will be forward or false if
	 *            backward.
	 * @return the calculated curve value for use with
	 *         {@link #radiusTurnDrive(double, double)}.
	 */
	public double startRadiusTurnToAngle(double angle, double radius, boolean forward) {
		turnToAngle(angle, false);
		this.radiusTurnOvershoot = 0;

		double curve = Math.exp((-radius) / props.effectiveWheelbase);
		curve = (forward == (angle >= 0)) ? curve : -curve;

		return curve;
	}

	public void radiusTurnDrive(double move, double curve) {
		this.robotDrive.drive(move, curve);
	}

	public boolean isRadiusTurnOnTarget() {
		boolean turnRateSignChange = false;
		if ((this.radiusTurnOvershoot == 0) && (this.turnRate != 0.0)) {
			this.radiusTurnOvershoot = (this.turnRate > 0.0) ? 1 : -1;
		} else {
			turnRateSignChange = (this.radiusTurnOvershoot > 0) != (this.turnRate > 0.0);
		}
		return turnRateSignChange || turnIsOnTarget();
	}

	public void stopRadiusTurnToAngle() {
		this.stopTurnToAngle();
	}

	/* END RADIUS TURN FUNCTIONS */

	/* START DRIVE TIMED TO VISION FUNCTIONS */

	public void startStraightToVision() {
		this.turnToAngle(Robot.getInstance().getVisionSubsystem().getTurnAng(), false);
	}

	public double getStraightToVisionTurnRate() {
		return this.getTurnRate();
	}
	
	public boolean isStraightToVisionTurnOnTarget() {
		return turnIsOnTarget();
	}

	public void stopStraightToVision() {
		this.stopTurnToAngle();
	}

	/* END DRIVE TIMED TO VISION FUNCTIONS */

	/* START TURN IMPLEMENTATION FUNCTIONS */

	/**
	 * Uses the gyro to turn a fixed angle in place.
	 * 
	 * @param angle
	 *            the angle to turn to.
	 * @param inPlace
	 *            true if this is an in place turn or false if driving with gyro
	 *            guided curve. Note that this parameter does not matter for a
	 *            radius turn but by conventions pass false since a radius turn
	 *            is not an in place turn.
	 */
	private void turnToAngle(double angle, boolean inPlace) {
		this.turnRate = 0.0;
		turnController.reset();
		turnController.enable();

		double newSetPoint = 0.0;
		double yaw = getGyroYaw();
		if ((yaw + angle) > 180) {
			newSetPoint = yaw + angle - 360;
		} else if ((yaw + angle) < -180) {
			newSetPoint = yaw + angle + 360;
		} else {
			newSetPoint = yaw + angle;
		}

		if (inPlace) {
			if (Math.abs(angle) < this.props.smallTurnAngle) {
				turnController.setPID(props.smallTurnPIDp, props.smallTurnPIDi, props.smallTurnPIDd);
			} else {
				turnController.setPID(props.turnPIDp, props.turnPIDi, props.turnPIDd);
			}
		} else {
			turnController.setPID(props.driveAndTurnPIDp, props.driveAndTurnPIDi, props.driveAndTurnPIDd);
		}
		turnController.setSetpoint(newSetPoint);
	}

	private double getGyroYaw() {
		return Math.IEEEremainder(gyro.getAngle(), 180.0);
	}

	private boolean turnIsOnTarget() {
		return turnController.onTarget();
	}

	private double getTurnRate() {
		return turnRate;
	}

	@Override
	public double pidGet() {
		return getGyroYaw();
	}

	@Override
	public void pidWrite(double output) {
		turnRate = output;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		gyro.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return gyro.getPIDSourceType();
	}

	private void stopTurnToAngle() {
		turnController.disable();
		stop();
	}

	/* END TURN IMPLEMENTATION FUNCTIONS */

	/**
	 * An instance of this class is used by the {@link DriveSubsystem} to manage
	 * the drive properties using the {@link ConfigurationSubsystem}.
	 */
	public class DriveProperties implements ConfigurationPropertiesConsumer {
		/**
		 * The value must be one of:
		 * <table>
		 * <tr>
		 * <th>Value</th>
		 * <th>Drive mode class</th>
		 * </tr>
		 * <tr>
		 * <th>0</th>
		 * <th>ArcadeDriveCommand</th>
		 * </tr>
		 * <tr>
		 * <th>1</th>
		 * <th>TankDriveCommand</th>
		 * </tr>
		 * <tr>
		 * <th>other</th>
		 * <th>SplitArcadeDriveCommand</th>
		 * </tr>
		 * </table>
		 */
		public static final String DRIVE_MODE_KEY = "drive.mode";
		public static final int DRIVE_MODE_VALUE_DEFAULT = 2;
		private int driveMode = DRIVE_MODE_VALUE_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String TURN_PID_TOLERANCE_KEY = "drive.turn.pid.tolerance";
		public static final double TURN_PID_TOLERANCE_DEFAULT = 1.0;
		private double turnPIDTolerance = TURN_PID_TOLERANCE_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String SMALL_TURN_ANGLE_KEY = "drive.small.turn.angle";
		public static final double SMALL_TURN_ANGLE_DEFAULT = 23.35;
		private double smallTurnAngle = SMALL_TURN_ANGLE_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String TURN_PID_P_KEY = "drive.turn.pid.p";
		public static final double TURN_PID_P_DEFAULT = 0.02; // 0.03
		private double turnPIDp = TURN_PID_P_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String TURN_PID_I_KEY = "drive.turn.pid.i";
		public static final double TURN_PID_I_DEFAULT = 0.0001; // 0.001
		private double turnPIDi = TURN_PID_I_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String TURN_PID_D_KEY = "drive.turn.pid.d";
		public static final double TURN_PID_D_DEFAULT = 0.00001; // 0.0004
		private double turnPIDd = TURN_PID_D_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String SMALL_TURN_PID_P_KEY = "drive.small.turn.pid.p";
		public static final double SMALL_TURN_PID_P_DEFAULT = 0.03;
		private double smallTurnPIDp = SMALL_TURN_PID_P_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String SMALL_TURN_PID_I_KEY = "drive.small.turn.pid.i";
		public static final double SMALL_TURN_PID_I_DEFAULT = 0.001;
		private double smallTurnPIDi = SMALL_TURN_PID_I_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String SMALL_TURN_PID_D_KEY = "drive.small.turn.pid.d";
		public static final double SMALL_TURN_PID_D_DEFAULT = 0.001;
		private double smallTurnPIDd = SMALL_TURN_PID_D_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String DRIVE_AND_TURN_PID_P_KEY = "drive.drive.and.turn.pid.p";
		public static final double DRIVE_AND_TURN_PID_P_DEFAULT = 0.03;
		private double driveAndTurnPIDp = DRIVE_AND_TURN_PID_P_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String DRIVE_AND_TURN_PID_I_KEY = "drive.drive.and.turn.pid.i";
		public static final double DRIVE_AND_TURN_PID_I_DEFAULT = 0.001;
		private double driveAndTurnPIDi = DRIVE_AND_TURN_PID_I_DEFAULT;

		/**
		 * The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String DRIVE_AND_TURN_PID_D_KEY = "drive.drive.and.turn.pid.d";
		public static final double DRIVE_AND_TURN_PID_D_DEFAULT = 0.001;
		private double driveAndTurnPIDd = DRIVE_AND_TURN_PID_D_DEFAULT;

		/**
		 * This is the effective wheelbase (distance between the axles in
		 * inches) of the robot. It is used to calculate curve values for radius
		 * turns. The value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String EFFECTIVE_WHEELBASE_KEY = "drive.effective.wheelbase";
		public static final double EFFECTIVE_WHEELBASE_DEFAULT = 22.0;
		private double effectiveWheelbase = EFFECTIVE_WHEELBASE_DEFAULT;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void reset(ConfigurationSubsystem config) {
			this.driveMode = ConfigurationUtilities.getProperty(config, DRIVE_MODE_KEY, DRIVE_MODE_VALUE_DEFAULT);

			this.turnPIDTolerance = ConfigurationUtilities.getProperty(config, TURN_PID_TOLERANCE_KEY,
					TURN_PID_TOLERANCE_DEFAULT);

			this.smallTurnAngle = ConfigurationUtilities.getProperty(config, SMALL_TURN_ANGLE_KEY,
					SMALL_TURN_ANGLE_DEFAULT);

			this.turnPIDp = ConfigurationUtilities.getProperty(config, TURN_PID_P_KEY, TURN_PID_P_DEFAULT);
			this.turnPIDi = ConfigurationUtilities.getProperty(config, TURN_PID_I_KEY, TURN_PID_I_DEFAULT);
			this.turnPIDd = ConfigurationUtilities.getProperty(config, TURN_PID_D_KEY, TURN_PID_D_DEFAULT);

			this.smallTurnPIDp = ConfigurationUtilities.getProperty(config, SMALL_TURN_PID_P_KEY,
					SMALL_TURN_PID_P_DEFAULT);
			this.smallTurnPIDi = ConfigurationUtilities.getProperty(config, SMALL_TURN_PID_I_KEY,
					SMALL_TURN_PID_I_DEFAULT);
			this.smallTurnPIDd = ConfigurationUtilities.getProperty(config, SMALL_TURN_PID_D_KEY,
					SMALL_TURN_PID_D_DEFAULT);

			this.driveAndTurnPIDp = ConfigurationUtilities.getProperty(config, DRIVE_AND_TURN_PID_P_KEY,
					DRIVE_AND_TURN_PID_P_DEFAULT);
			this.driveAndTurnPIDi = ConfigurationUtilities.getProperty(config, DRIVE_AND_TURN_PID_I_KEY,
					DRIVE_AND_TURN_PID_I_DEFAULT);
			this.driveAndTurnPIDd = ConfigurationUtilities.getProperty(config, DRIVE_AND_TURN_PID_D_KEY,
					DRIVE_AND_TURN_PID_D_DEFAULT);
		}
	}
}
