package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A simple autonomous command that just drives over the baseline for 5 points.
 */
public class DriveBaseline extends CommandGroup {
	public DriveBaseline() {
		addSequential(new DriveRobot(0.3, 0.0, 5000));
	}
}
