package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.GearSub;
import org.usfirst.frc.team2357.robot.subsystems.GearSub.DoorPosition;

/**
 *
 */
public class PingGearBinAuto extends PingGearBinManual {
	private PingGearBinAutoCloseDoor closeCmd = new PingGearBinAutoCloseDoor();

	public PingGearBinAuto() {
		super();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		GearSub gs = Robot.INSTANCE.gearSubsystem;
		if (gs.isPegged()) {
			gs.setDoorPosition(DoorPosition.OPEN);
		} else {
			closeCmd.start();
		}
		super.execute();
	}
}
