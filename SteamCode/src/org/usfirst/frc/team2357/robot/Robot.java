
package org.usfirst.frc.team2357.robot;



import org.usfirst.frc.team2357.robot.subsystems.ClimberSub;
//import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2357.robot.subsystems.DriveSub;
import org.usfirst.frc.team2357.robot.subsystems.GearSub;
import org.usfirst.frc.team2357.robot.subsystems.PneumaticSub;
//import org.usfirst.frc.team2357.robot.subsystems.VisionSub;
import org.usfirst.frc.team2357.robot.subsystems.VisionSub;
import org.usfirst.frc.team2357.robot.AutonomousModes.BoilersideGearAuto;
import org.usfirst.frc.team2357.robot.AutonomousModes.DriveBaseLine;

//import edu.wpi.cscore.CvSink;
//import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Robot INSTANCE;
	public final DriveSub driveSubsystem = new DriveSub(RobotMap.PIDp, RobotMap.PIDi, RobotMap.PIDd);
	public OI oi;
	public PneumaticSub pneumaticSubsystem = new PneumaticSub();
	public GearSub gearSubsystem = new GearSub();
	public VisionSub visionSubsystem = new VisionSub();
	public ClimberSub climberSubsystem = new ClimberSub();
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	public Preferences prefs;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		INSTANCE = this;
		
		oi = new OI();
		chooser.addDefault("Drive to Baseline", new DriveBaseLine());
		chooser.addObject("Boiler Side Gear", new BoilersideGearAuto());
		SmartDashboard.putData("Auto mode", chooser);
		//visionSubsystem.startVisionThread();
		
		prefs = Preferences.getInstance();
		prefs.getDouble("PegRLow", 0.0);
		prefs.getDouble("PegRHigh", 155.0);
		prefs.getDouble("PegGLow", 197.0);
		prefs.getDouble("PegGHigh", 255.0);
		prefs.getDouble("PegBLow", 135.0);
		prefs.getDouble("PegBHigh", 255.0);
		
		prefs.getDouble("FeederRLow", 0.0);
		prefs.getDouble("FeederRHigh", 155.0);
		prefs.getDouble("FeederGLow", 197.0);
		prefs.getDouble("FeederGHigh", 255.0);
		prefs.getDouble("FeederBLow", 135.0);
		prefs.getDouble("FeederBHigh", 255.0);
		
		
 	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) chooser.getSelected();
		

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		//if (driveMode != null){
		//	Config.setDriveMode(true);
		//}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//System.out.println("TurnAng:" + visionSubsystem.getTurnAng());
		System.out.println("TurnOutput:" + driveSubsystem.getTurnRate());
		//System.out.println(visionSubsystem.);
		driveSubsystem.printYaw();
		driveSubsystem.printSetpoint();
		/*driveSubsystem.printError();
		driveSubsystem.printSetpoint();
		System.out.println(driveSubsystem.getTurnRate());*/
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void getSmartDashboard() {
		// TODO Auto-generated method stub
		
	}
}
