package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobot extends Command {
	
	private double drv = 0.0;
	private double rot = 0.0;
	private double ms = 0.0;
	
	/**
	 * 
	 * Do not use without wanting to drive forever.
	 * 
	 * @param DriveSet
	 * @param RotateSet
	 * 
	 * 
	 */
    public DriveRobot(double DriveSet, double RotateSet) {
    	
    	
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drv = DriveSet;
    	rot = RotateSet;
    	ms = -1.0;
    	requires(Robot.INSTANCE.driveSubsystem);
    }
    
    public DriveRobot(double DriveSet, double RotateSet, double msTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drv = DriveSet;
    	rot = RotateSet;
    	ms = msTime;
    	requires(Robot.INSTANCE.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println(this.getName() + "Start Time: " + System.currentTimeMillis());
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0.0, 0.0);
    	if(ms != -1.0)
    	{
    		setTimeout(ms/1000.0);
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(-drv, -rot);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(ms != -1.0)
    	{
    		return isTimedOut();
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0.0, 0.0);
    	System.out.println(this.getName() + "Stop Time: " + System.currentTimeMillis());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
