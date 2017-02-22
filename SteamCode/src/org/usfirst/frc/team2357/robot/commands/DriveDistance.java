package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class DriveDistance extends Command {

	private double distance;
	
    public DriveDistance(double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.INSTANCE.driveSubsystem);
    	
    	distance = ((inches/18.85) * 5.4);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.INSTANCE.driveSubsystem.enablePositionalDrive();
    	Robot.INSTANCE.driveSubsystem.setMoveDistance(distance);
    	Robot.INSTANCE.driveSubsystem.moveDist();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("encSetpoint = " + distance);
		System.out.println("Left enc Position:" + Robot.INSTANCE.driveSubsystem.getLeftDrive().getEncPosition() + " Right encPosition:" + Robot.INSTANCE.driveSubsystem.getRightDrive().getEncPosition());
		Robot.INSTANCE.driveSubsystem.printLeftDriveErr();
		Robot.INSTANCE.driveSubsystem.printRightDriveErr();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.INSTANCE.driveSubsystem.isPositionOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.driveSubsystem.disablePositionalDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
