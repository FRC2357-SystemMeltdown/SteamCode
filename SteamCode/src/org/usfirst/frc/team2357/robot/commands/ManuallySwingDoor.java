package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.GearSub2;
import org.usfirst.frc.team2357.robot.subsystems.GearSub2.DoorPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManuallySwingDoor extends Command {

	public ManuallySwingDoor() {
		// DO NOT REQUIRE THE GEARBOX
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		GearSub2 gs = Robot.INSTANCE.gearSubsystem2;
		if (gs.getDoorPosition() == DoorPosition.CLOSED) {
			gs.setDoorPosition(DoorPosition.OPEN);
		} else {
			gs.setDoorPosition(DoorPosition.CLOSED);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
