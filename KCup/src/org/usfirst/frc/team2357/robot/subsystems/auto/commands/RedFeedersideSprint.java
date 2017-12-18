package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns.RadiusTurnToFixedAngle;

import edu.wpi.first.wpilibj.command.AbstractStateCommandGroup;

public class RedFeedersideSprint extends AbstractStateCommandGroup {
	public RedFeedersideSprint() {
		addSequential(new RadiusTurnToFixedAngle(-60.0, 36.0, -0.4, 2.0));
		addSequential(new DriveRobot(0.4, 0.0, 5000));
	}
}
