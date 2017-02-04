package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class DriveToTarget extends PIDCommand {
	
	public DriveToTarget(double p, double i, double d) {
		super(p, i, d);
		// TODO Auto-generated constructor stub
		requires(Robot.INSTANCE.driveSubsystem);
    	requires(Robot.INSTANCE.visionSubsystem);
	}

	private double centerx;
	private double turn;


    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().enable();
    	setSetpoint((RobotMap.imgWidth / 2));
    	//getPIDController().
    	
    	//super.getPIDController().
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	synchronized (Robot.INSTANCE.visionSubsystem.getImgLock())
    	{
    		centerx = Robot.INSTANCE.visionSubsystem.getCenterX();
    		System.out.println("centerx: " + centerx);
    	}
    	System.out.println("turn: " + turn);
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0.0, -turn);
    	//TODO use different constant for urning if in speed or strength mode
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0, 0);
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return centerx;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		turn = output;
	}
	
}
