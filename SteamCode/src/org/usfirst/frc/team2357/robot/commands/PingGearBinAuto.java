package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.GearSub2;
import org.usfirst.frc.team2357.robot.subsystems.GearSub2.DoorPosition;

/**
 *
 */
public class PingGearBinAuto extends PingGearBinManual {
	public PingGearBinAuto() {
		super();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		GearSub2 gs = Robot.INSTANCE.gearSubsystem2;
		if (gs.isPegged()) {
			gs.setDoorPosition(DoorPosition.OPEN);
		} else {
			gs.setDoorPosition(DoorPosition.CLOSED);
		}
		super.execute();
	}
}
