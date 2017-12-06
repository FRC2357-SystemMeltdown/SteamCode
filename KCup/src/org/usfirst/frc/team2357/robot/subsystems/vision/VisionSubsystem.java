package org.usfirst.frc.team2357.robot.subsystems.vision;

import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationPropertiesConsumer;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationSubsystem;
import org.usfirst.frc.team2357.robot.subsystems.configuration.ConfigurationUtilities;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The vision subsystem starts a separate thread to periodically run the
 * {@link GripPipeline}. The pipeline can be set to scan for the vision or
 * feeder target via the {@link #setAlignedToFeeder(boolean)} method. In either
 * case, it updates the current angle to the target on each pipeline iteration.
 * The most recent angle is available via the {@link #getTurnAng()} method.
 */
public class VisionSubsystem extends Subsystem {
	private final VisionThread visionThread;
	private boolean alignedToFeeder = false;
	private double turnAng;
	private int threadMiscount = 0;
	final VisionProperties props = new VisionProperties();

	/**
	 * Registers with the configuration subsystem (vision properties set at this
	 * time), sets up the camera and starts the pipeline thread.
	 */
	public VisionSubsystem() {
		super();
		Robot.getInstance().getConfigurationSubsystem().addConsumer(this.props);

		UsbCamera visionCamera = CameraServer.getInstance().startAutomaticCapture(0);

		// TODO there is some more properties work to do here.
		visionCamera.setFPS(20);
		visionCamera.setResolution(props.imageWidth, props.imageHeight);
		visionCamera.setExposureManual(25);
		visionCamera.setBrightness(25);
		visionCamera.setWhiteBalanceManual(4500);

		visionThread = new VisionThread(visionCamera, new GripPipeline(this), pipeline -> {
			try {
				List<MatOfPoint> contours = pipeline.filterContoursOutput();
				if (!contours.isEmpty() && contours.size() >= 2) {
					threadMiscount = 0;
					MatOfPoint contour1 = contours.get(0);
					MatOfPoint contour2 = contours.get(1);
					for (int i = 2; i < contours.size(); i++) {
						MatOfPoint nextContour = contours.get(i);
						if (nextContour.elemSize() > contour1.elemSize()) {
							contour1 = nextContour;
						} else if (nextContour.elemSize() > contour2.elemSize()) {
							contour2 = nextContour;
						}
					}

					Rect r1 = Imgproc.boundingRect(contour1);
					Rect r2 = Imgproc.boundingRect(contour2);
					// double centerX = (((r1.x + (r1.width / 2)) + ((r2.x +
					// r2.width) - (r2.width / 2))) / 2);

					int targetPixelWidth = (r2.x + r2.width) - r1.x;
					int targetPixelOffset = (int) ((this.props.cameraOffset * targetPixelWidth) / 10.25);
					double centerX = (((r1.x + (r1.width / 2)) + ((r2.x + r2.width) - (r2.width / 2))) / 2);
					centerX = centerX + targetPixelOffset;

					double turnRatio = (centerX / (props.imageWidth / 2)) - 1;
					turnAng = turnRatio * this.props.cameraFOV;
				} else if ((!contours.isEmpty() && contours.size() == 1)) {
					threadMiscount = 0;
					MatOfPoint contour1 = contours.get(0);
					for (int i = 2; i < contours.size(); i++) {
						MatOfPoint nextContour = contours.get(i);
						if (nextContour.elemSize() > contour1.elemSize()) {
							contour1 = nextContour;
						}
					}
					Rect r1 = Imgproc.boundingRect(contour1);
					double centerX = r1.x + (r1.width / 2);
					double turnRatio = (centerX / (props.imageWidth / 2)) - 1;
					turnAng = turnRatio * this.props.cameraFOV + 3.0;
				} else if (threadMiscount > 5) {
					turnAng = 0.0;
				} else {
					threadMiscount++;
				}
				SmartDashboard.putNumber("TurnAng", turnAng);
				VisionThread.sleep(100);
			} catch (Throwable e) {
				System.out.println("Error 1");
				e.printStackTrace();
			}
		});
		visionThread.start();
	}

	/**
	 * Returns the latest angle to target from the pipeline thread.
	 * 
	 * @return the latest calculated angle. Will be 0.0 for no detected target.
	 */
	public double getTurnAng() {
		return turnAng;
	}

	/**
	 * @return true if currently searching for feeder and false if searching for
	 *         the peg vision targets.
	 */
	public boolean searchingForFeeder() {
		return alignedToFeeder;
	}

	/**
	 * @param alignedToFeeder
	 *            pass in true to start looking for the feeder and false to
	 *            search for the peg vision targets.
	 */
	public void setAlignedToFeeder(boolean alignedToFeeder) {
		this.alignedToFeeder = alignedToFeeder;
	}

	/**
	 * There is no default command for this subsystem.
	 */
	public void initDefaultCommand() {
	}

	/**
	 * An instance of this class is used by the {@link VisionSubsystem} to
	 * manage the gear handling properties using the
	 * {@link ConfigurationSubsystem}.
	 */
	public class VisionProperties implements ConfigurationPropertiesConsumer {
		/**
		 * This is the front camera's field of vision (FOV) in degrees. The
		 * value must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String CAMERA_FOV_KEY = "vision.camera.fov";
		public static final double CAMERA_FOV_DEFAULT = 23.35;
		private double cameraFOV = CAMERA_FOV_DEFAULT;

		/**
		 * This is the front camera's offset in inches from center. The value
		 * must be parseable into a double via
		 * {@link Double#parseDouble(String)} or the default value will be used.
		 */
		public static final String CAMERA_OFFSET_KEY = "vision.camera.offset";
		public static final double CAMERA_OFFSET_DEFAULT = 1.75;
		private double cameraOffset = CAMERA_OFFSET_DEFAULT;

		/**
		 * This is the front camera's image width in pixels. The value must be
		 * parseable into an int via {@link Integer#parseInt(String)} or the
		 * default value will be used.
		 */
		public static final String IMAGE_WIDTH_KEY = "vision.image.width";
		public static final int IMAGE_WIDTH_DEFAULT = 320;
		private int imageWidth = IMAGE_WIDTH_DEFAULT;

		/**
		 * This is the front camera's image height in pixels. The value must be
		 * parseable into an int via {@link Integer#parseInt(String)} or the
		 * default value will be used.
		 */
		public static final String IMAGE_HEIGHT_KEY = "vision.image.height";
		public static final int IMAGE_HEIGHT_DEFAULT = 240;
		private int imageHeight = IMAGE_HEIGHT_DEFAULT;

		/**
		 * These are the RGB values for the peg vision target (low threshold).
		 * The property must be three comma separated values which are parseable
		 * into a double via {@link Double#parseDouble(String)} or the default
		 * value will be used.
		 */
		public static final String PEG_TARGET_RGB_LOW_KEY = "vision.peg.target.rgb.low";
		private /* public static */ final double[] PEG_TARGET_RGB_LOW_DEFAULT = { 0.0, 197.0, 135.0 };
		double pegTargetRGBLow[] = PEG_TARGET_RGB_LOW_DEFAULT;

		/**
		 * These are the RGB values for the peg vision target (high threshold).
		 * The property must be three comma separated values which are parseable
		 * into a double via {@link Double#parseDouble(String)} or the default
		 * value will be used.
		 */
		public static final String PEG_TARGET_RGB_HIGH_KEY = "vision.peg.target.rgb.high";
		private /* public static */ final double[] PEG_TARGET_RGB_HIGH_DEFAULT = { 155.0, 255.0, 255.0 };
		double pegTargetRGBHigh[] = PEG_TARGET_RGB_HIGH_DEFAULT;

		/**
		 * These are the RGB values for the feeder vision target (low
		 * threshold). The property must be three comma separated values which
		 * are parseable into a double via {@link Double#parseDouble(String)} or
		 * the default value will be used.
		 */
		public static final String FEEDER_TARGET_RGB_LOW_KEY = "vision.feeder.target.rgb.low";
		private /* public static */ final double[] FEEDER_TARGET_RGB_LOW_DEFAULT = { 0.0, 197.0, 135.0 };
		double feederTargetRGBLow[] = FEEDER_TARGET_RGB_LOW_DEFAULT;

		/**
		 * These are the RGB values for the feeder vision target (high
		 * threshold). The property must be three comma separated values which
		 * are parseable into a double via {@link Double#parseDouble(String)} or
		 * the default value will be used.
		 */
		public static final String FEEDER_TARGET_RGB_HIGH_KEY = "vision.feeder.target.rgb.high";
		private /* public static */ final double[] FEEDER_TARGET_RGB_HIGH_DEFAULT = { 155.0, 255.0, 255.0 };
		double feederTargetRGBHigh[] = FEEDER_TARGET_RGB_HIGH_DEFAULT;

		@Override
		public void reset(ConfigurationSubsystem config) {
			this.cameraFOV = ConfigurationUtilities.getProperty(config, CAMERA_FOV_KEY, CAMERA_FOV_DEFAULT);
			this.imageWidth = ConfigurationUtilities.getProperty(config, IMAGE_WIDTH_KEY, IMAGE_WIDTH_DEFAULT);
			this.imageHeight = ConfigurationUtilities.getProperty(config, IMAGE_HEIGHT_KEY, IMAGE_HEIGHT_DEFAULT);
			this.pegTargetRGBLow = ConfigurationUtilities.getProperty(config, PEG_TARGET_RGB_LOW_KEY,
					PEG_TARGET_RGB_LOW_DEFAULT, true);
			this.pegTargetRGBHigh = ConfigurationUtilities.getProperty(config, PEG_TARGET_RGB_HIGH_KEY,
					PEG_TARGET_RGB_HIGH_DEFAULT, true);
			this.feederTargetRGBLow = ConfigurationUtilities.getProperty(config, FEEDER_TARGET_RGB_LOW_KEY,
					FEEDER_TARGET_RGB_LOW_DEFAULT, true);
			this.feederTargetRGBHigh = ConfigurationUtilities.getProperty(config, FEEDER_TARGET_RGB_HIGH_KEY,
					FEEDER_TARGET_RGB_HIGH_DEFAULT, true);
		}
	}
}
