package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.Config;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team2357.robot.commands.SplitAcrcadeDriveCommand;
import org.usfirst.frc.team2357.robot.commands.TankDriveCommand;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;


/**
 *
 */
public class DriveSub extends Subsystem implements PIDOutput, PIDSource {
	private CANTalon leftDrive = new CANTalon(RobotMap.leftDrive1);
	private CANTalon rightDrive = new CANTalon(RobotMap.rightDrive1);
	private CANTalon leftDriveSlave = new CANTalon(RobotMap.leftDrive2);
	private CANTalon rightDriveSlave = new CANTalon(RobotMap.rightDrive2);
	private RobotDrive robotDrive = new RobotDrive(getLeftDrive(), getRightDrive());

	private AnalogGyro gyro = new AnalogGyro(1);
	private PIDController turnController;
	
	private double turnRate;
	private double encSetpoint = 0;
	
	
	public DriveSub(double p, double i, double d){
		super();
		/*try {
			ahrs = new AHRS(SerialPort.Port.kUSB);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error instantiating AHRS...");
		}*/
		
		//ahrs.reset();
		turnController = new PIDController(p, i, d, 0.0, this , this, 0.01);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(RobotMap.PIDtol);
		turnController.setContinuous(true);
		//turnController.setToleranceBuffer(5);
		
		
		
		getLeftDrive().setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		getLeftDrive().configEncoderCodesPerRev(128);
		getLeftDrive().setControlMode(0);
		leftDrive.setInverted(true);
		leftDriveSlave.setInverted(true);
		leftDrive.setAllowableClosedLoopErr(50);
		getLeftDrive().setPID(0.5, 0.0005, 0.0);
		getRightDrive().setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		getRightDrive().configEncoderCodesPerRev(128);
		getRightDrive().setControlMode(0);
		rightDrive.setAllowableClosedLoopErr(50);
		getRightDrive().setPID(0.5, 0.0005, 0.0);
		rightDrive.setInverted(true);
		rightDriveSlave.setInverted(true);
		
		leftDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftDriveSlave.set(RobotMap.leftDrive1);
		//leftDriveSlave.reverseOutput(true);
		rightDriveSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightDriveSlave.set(RobotMap.rightDrive1);
		//rightDriveSlave.reverseOutput(true);
		robotDrive.setSafetyEnabled(false);
		
		// SFW ADDED
		//brakeMode(true);
	}
	
	private void brakeMode(boolean brake) {
		leftDrive.enableBrakeMode(brake);
		rightDrive.enableBrakeMode(brake);
		leftDriveSlave.enableBrakeMode(brake);
		rightDriveSlave.enableBrakeMode(brake);
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
    	robotDrive.arcadeDrive(-moveValue, -rotateValue);
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
    	//ahrs.zeroYaw();
    	//System.out.println(ahrs.getYaw() + " = yaw");
    	//System.out.println(angle + " = angle");
    	turnController.reset();
    			
    	turnController.enable();
    	/*if((ahrs.getYaw() + angle) > 180)
    	{
    		turnController.setSetpoint(ahrs.getYaw() + angle - 360);
    	} else if((ahrs.getYaw() + angle) < -180)
    	{
    		turnController.setSetpoint(ahrs.getYaw() + angle + 360);
    	} else {
    		turnController.setSetpoint(ahrs.getYaw() + angle);
        	
    	}*/
    	
    	double newSetPoint = 0.0;
    	if((getGyroYaw() + angle) > 180)
    	{
    		newSetPoint = getGyroYaw() + angle - 360;
    	} else if((getGyroYaw() + angle) < -180)
    	{
    		newSetPoint = getGyroYaw() + angle + 360;
    	} else {
    		newSetPoint = getGyroYaw() + angle;
    	}
    	if (Math.abs(angle) < RobotMap.cameraFOVConst)
    	{
    		turnController.setPID(RobotMap.PIDp_SmallTurns, RobotMap.PIDi_SmallTurns, RobotMap.PIDd_SmallTurns);
    	} else {
    		turnController.setPID(RobotMap.PIDp, RobotMap.PIDi, RobotMap.PIDd);
    	}
    	turnController.setSetpoint(newSetPoint);
	}
    
    public boolean turnIsOnTarget() {
    	//System.out.println("TurnCtrlrErr:" + turnController.getError());
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
		
		getLeftDrive().changeControlMode(CANTalon.TalonControlMode.Position);
		getLeftDrive().setVoltageRampRate(0);
		getLeftDrive().setEncPosition(0);
		getRightDrive().changeControlMode(CANTalon.TalonControlMode.Position);
		getRightDrive().setVoltageRampRate(0);
		getRightDrive().setEncPosition(0);
	}
	public void disablePositionalDrive() {
		// TODO Auto-generated method stub
		getLeftDrive().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		getRightDrive().changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	
	
	public void moveDist()
	{
		getLeftDrive().setSetpoint(-encSetpoint);
		getRightDrive().setSetpoint(encSetpoint);		
	}
	
	public void setMoveDistance(double dist)
	{
		encSetpoint = dist;
	}
     
	public void printYaw()
	{
		//System.out.println("Yaw:" + ahrs.getYaw());
		System.out.println("Yaw:" + getGyroYaw());
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
	
	public boolean isPositionOnTarget()
	{
		return((Math.abs(leftDrive.getClosedLoopError()) <= 50) && (Math.abs(rightDrive.getClosedLoopError()) <= 50));
	}
	
	private double getGyroYaw()
	{
		double in = gyro.getAngle();
		double out = in;
		if (Math.abs(in) > 180.0)
		{
			if (in > 0)
			{
				//out = (in - ((((int)in / 360) + 1) * 360));
				out = -180 + (Math.abs(in + 180) % 360);
			} else if(in < 0) {
				//out = (in + ((((-(int)in) / 360) + 1) * 360));
				out = 180 - (Math.abs(in + 180) % 360);
			}
		}
		return out;
	}

	public CANTalon getLeftDrive() {
		return leftDrive;
	}

	public void setLeftDrive(CANTalon leftDrive) {
		this.leftDrive = leftDrive;
	}

	public CANTalon getRightDrive() {
		return rightDrive;
	}

	public void setRightDrive(CANTalon rightDrive) {
		this.rightDrive = rightDrive;
	}
	
	public void printLeftDriveErr()
	{
		System.out.println("LeftErr:" + leftDrive.getError());
	}
	
	public void printRightDriveErr()
	{
		System.out.println("RightErr:" + rightDrive.getError());	
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		gyro.setPIDSourceType(pidSource);		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return gyro.getPIDSourceType();
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return getGyroYaw();
	}

}

