package org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.gear.GearSubsystem;

public class DriveRobotToVisionUntilPegged extends DriveRobotToVision {
	private final GearSubsystem gearSubsytem;

	public DriveRobotToVisionUntilPegged(double moveValue, double msTime) {
		super(moveValue, msTime);
		this.gearSubsytem = Robot.getInstance().getGearSubsystem();
	}

	@Override
	protected boolean isFinished() {
		return super.isFinished() || this.gearSubsytem.isPegged();
	}
}
