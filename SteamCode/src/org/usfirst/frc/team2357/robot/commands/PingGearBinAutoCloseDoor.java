package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.GearSub.DoorPosition;

/**
 *
 */
public class PingGearBinAutoCloseDoor extends PingGearBinManual {
	public PingGearBinAutoCloseDoor() {
		super();
		setTimeout(1.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.INSTANCE.gearSubsystem.setDoorPosition(DoorPosition.CLOSED);
	}
}
