package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobot extends Command {
	
	private int drv = 0;
	private int rot = 0;
	private int msTime = 0;

    public DriveRobot(int DriveSet, int RotateSet) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drv = DriveSet;
    	rot = RotateSet;
    	requires(Robot.INSTANCE.driveSubsystem);
    }
    
    public DriveRobot(int DriveSet, int RotateSet, int msTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drv = DriveSet;
    	rot = RotateSet;
    	this.msTime = msTime;
    	requires(Robot.INSTANCE.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(drv, rot);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(msTime != 0)
    	{
    		long clockSet = System.currentTimeMillis();
    		if (System.currentTimeMillis() == (clockSet + msTime))
    		{
    			return true;
    		}
    		
    		
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0, 0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}