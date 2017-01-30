package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class centerOnTarget extends Command {
	
	private double centerx;
	private double turn;

    public centerOnTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.INSTANCE.driveSubsystem);
    	requires(Robot.INSTANCE.visionSubsystem);
    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	synchronized (Robot.INSTANCE.visionSubsystem.getImgLock())
    	{
    		centerx = Robot.INSTANCE.visionSubsystem.getCenterX();
    		System.out.println("centerx: " + centerx);
    	}
    	turn = centerx - (RobotMap.imgWidth / 2);
    	System.out.println("turn: " + turn);
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0.0, turn * 0.004);
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
}
