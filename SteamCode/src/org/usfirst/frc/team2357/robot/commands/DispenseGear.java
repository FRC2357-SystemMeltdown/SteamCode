package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DispenseGear extends Command {

	private double timeCheck;
    public DispenseGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.INSTANCE.gearSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timeCheck = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INSTANCE.gearSubsystem.gearOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;/*(Robot.INSTANCE.gearSubsystem.isLeftServoOnTarget() && Robot.INSTANCE.gearSubsystem.isRightServoOnTarget());*/
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.gearSubsystem.gearIn();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
