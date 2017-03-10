package org.usfirst.frc.team2357.robot.commands;


import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilPegged extends DriveDistance {

    public DriveUntilPegged(double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(inches);
    }



    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return super.isFinished() || Robot.INSTANCE.gearSubsystem.isPegged();
    }


}
