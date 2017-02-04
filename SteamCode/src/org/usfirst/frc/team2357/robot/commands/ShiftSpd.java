package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.XboxController;


/**
 *
 */
public class ShiftSpd extends Command {
	private static boolean switchStatus = false;

    public ShiftSpd() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.INSTANCE.pneumaticSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INSTANCE.pneumaticSubsystem.shiftSpeed(/*Robot.pneumaticSubsystem.*/);
    	
    	
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.INSTANCE.pneumaticSubsystem.stopPistonSol();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.INSTANCE.pneumaticSubsystem.stopPistonSol();
    }
    
    public static void setSwitchStatus(boolean switchStatus) {
		ShiftSpd.switchStatus = switchStatus;
	}
    
    public static boolean getSwitchStatus(){
    	return switchStatus;
    }
}
