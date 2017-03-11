package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ForeverDriveToPeg extends DriveRobot {

    public ForeverDriveToPeg(double driveVal, double rotateVal) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(driveVal, rotateVal);
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.INSTANCE.gearSubsystem.isPegged();
    }

   
}
