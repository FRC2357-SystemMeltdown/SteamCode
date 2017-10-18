package org.usfirst.frc.team2357.robot.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.GearSub;
import org.usfirst.frc.team2357.robot.subsystems.GearSub.DoorPosition;

/**
 *
 */
public class PingGearBinAuto extends PingGearBinManual {
	private PingGearBinAutoCloseDoor closeCmd = new PingGearBinAutoCloseDoor();
	GearSub gs = Robot.INSTANCE.gearSubsystem;
	private boolean oldPos = false;

	public PingGearBinAuto() {
		super();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (gs.isPegged()) {
			gs.setDoorPosition(DoorPosition.OPEN);
			oldPos = true;
		}
		if(!gs.isPegged() && oldPos){
			closeCmd.start();
			oldPos = false;
		}
		super.execute();
	}
}
