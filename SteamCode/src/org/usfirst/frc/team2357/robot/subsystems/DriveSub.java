package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.Config;
import org.usfirst.frc.team2357.robot.commands.*;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;


/**
 *
 */
public class DriveSub extends PIDSubsystem {
	private CANTalon leftDrive = new CANTalon(RobotMap.leftDrive1);
	private CANTalon rightDrive = new CANTalon(RobotMap.rightDrive1);
	private CANTalon leftDriveSlave = new CANTalon(RobotMap.leftDrive2);
	private CANTalon rightDriveSlave = new CANTalon(RobotMap.rightDrive2);
	private RobotDrive robotDrive = new RobotDrive(leftDrive, rightDrive);
	private AHRS ahrs = new AHRS(Port.kUSB);
	private PIDController turnController;
	
	private double turnRate;
	
	
	public DriveSub(double p, double i, double d){
		super(p,i,d);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(RobotMap.PIDtol);
		turnController.setContinuous(true);
		
		
		
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
    	if (Config.driveMode == true){
    		setDefaultCommand(new ArcadeDriveCommand());
    	}else{
    		setDefaultCommand(new TankDriveCommand());
    	}
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
    	robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void tankDrive(double leftValue, double rightValue){
    	robotDrive.tankDrive(leftValue, rightValue);
    }
    public void stop() {
    	arcadeDrive(0.0, 0.0);
    }
    
    public void turnAngle(double angle) {
		// TODO Auto-generated method stub
    	ahrs.zeroYaw();
    	turnController.enable();
    	turnController.setSetpoint(angle);
	}
    
    @Override
    public double getSetpoint() {
    	// TODO Auto-generated method stub
    	return super.getSetpoint();
    }
    
    private double getYaw() {
		// TODO Auto-generated method stub
    	return ahrs.getYaw();
	}
    
    public double getTurnRate() {
		return turnRate;
	}
    
    @Override
    protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return ahrs.pidGet();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		turnRate = output;
	}
     
}

