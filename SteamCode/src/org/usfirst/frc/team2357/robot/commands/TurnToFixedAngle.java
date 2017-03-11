package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToFixedAngle extends Command {
	private double targetAngle = 0.0;
	private boolean allianceSensitive = false;
	
	public TurnToFixedAngle(double angle) {
		this(angle, false);
	}
	
	public TurnToFixedAngle(double angle, boolean allianceSensitive) {
		requires(Robot.INSTANCE.driveSubsystem);
		
		targetAngle = angle; //SmartDashboard.getNumber("TurnFixedAngle", 0.0);
		this.allianceSensitive = allianceSensitive;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	if (allianceSensitive) {
    		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
    			this.targetAngle = this.targetAngle * -1.0;
    		}
    	}
    	Robot.INSTANCE.driveSubsystem.turnAngle(targetAngle);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(
    			0.0,
    			Robot.INSTANCE.driveSubsystem.getTurnRate());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.INSTANCE.driveSubsystem.turnIsOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.driveSubsystem.stopPID();
    	Robot.INSTANCE.driveSubsystem.arcadeDrive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
