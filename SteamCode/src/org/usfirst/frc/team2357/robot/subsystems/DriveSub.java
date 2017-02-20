package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.Config;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team2357.robot.commands.SplitAcrcadeDriveCommand;
import org.usfirst.frc.team2357.robot.commands.TankDriveCommand;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSub extends Subsystem implements PIDOutput {
	private CANTalon leftDrive = new CANTalon(RobotMap.leftDrive1);
	private CANTalon rightDrive = new CANTalon(RobotMap.rightDrive1);
	private CANTalon leftDriveSlave = new CANTalon(RobotMap.leftDrive2);
	private CANTalon rightDriveSlave = new CANTalon(RobotMap.rightDrive2);
	private RobotDrive robotDrive = new RobotDrive(leftDrive, rightDrive);
	private AHRS ahrs;
	private PIDController turnController;
	
	private double turnRate;
	private int encPosition = 0;
	
	
	public DriveSub(double p, double i, double d){
		super();
		try {
			ahrs = new AHRS(SerialPort.Port.kUSB);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error instantiating AHRS...");
		}
		
		//ahrs.reset();
		turnController = new PIDController(p, i, d, 0.0, ahrs, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(RobotMap.PIDtol);
		turnController.setContinuous(true);
		
		leftDrive.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftDrive.configEncoderCodesPerRev(128);
		leftDrive.setControlMode(0);
		rightDrive.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightDrive.configEncoderCodesPerRev(128);
		rightDrive.setControlMode(0);
	
		leftDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftDriveSlave.set(RobotMap.leftDrive1);
		//leftDriveSlave.reverseOutput(true);
		rightDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightDriveSlave.set(RobotMap.rightDrive1);
		//rightDriveSlave.reverseOutput(true);
		robotDrive.setSafetyEnabled(false);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	if (Config.driveMode == 0){
    		setDefaultCommand(new ArcadeDriveCommand());
    	}else if(Config.driveMode == 1){
    		setDefaultCommand(new TankDriveCommand());
    	}else{
    		setDefaultCommand(new SplitAcrcadeDriveCommand());
    	}
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
    	robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void tankDrive(double leftValue, double rightValue){
    	robotDrive.tankDrive(leftValue, rightValue);
    }
    public void stopPID() {
    	turnController.disable();
    	arcadeDrive(0.0, 0.0);
    }
    
    public void turnAngle(double angle) {
		// TODO Auto-generated method stub
    	ahrs.reset();
    	System.out.println(ahrs.getYaw() + " = yaw");
    	System.out.println(angle + " = angle");
    	turnController.reset();
    			
    	turnController.enable();
    	turnController.setSetpoint(angle);
	}
    
    public boolean turnIsOnTarget() {
    	return turnController.onTarget();
    }
    
    public double getTurnRate() {
		return turnRate;
	}

	@Override
	public void pidWrite(double output) {
		turnRate = output;
	}
	
	public void enablePositionalDrive()
	{
		leftDrive.changeControlMode(CANTalon.TalonControlMode.Position);
		leftDrive.setVoltageRampRate(0);
		leftDrive.setEncPosition(encPosition);
		rightDrive.changeControlMode(CANTalon.TalonControlMode.Position);
		rightDrive.setVoltageRampRate(0);
		rightDrive.setEncPosition(encPosition);
	}
	public void disablePositionalDrive() {
		// TODO Auto-generated method stub
		leftDrive.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		rightDrive.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	public void moveDist(int inchesForward)
	{
		encPosition += inchesForward;
		leftDrive.setEncPosition(encPosition);
		rightDrive.setEncPosition(encPosition);
	}
     
	public void printYaw()
	{
		System.out.println("Yaw:" + ahrs.getYaw());
	}
	
	public void printError()
	{
		System.out.println("Error:" + turnController.getError());
	}
	public void printSetpoint()
	{
		System.out.println("Setpoint:" + turnController.getSetpoint());
	}
	
	public boolean isOnTarget()
	{
		return(turnController.onTarget());
	}
}

