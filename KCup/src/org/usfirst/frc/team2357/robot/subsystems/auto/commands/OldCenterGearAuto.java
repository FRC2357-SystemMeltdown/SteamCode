package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.ForeverDriveToPeg;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns.InPlaceTurnToVisionAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class OldCenterGearAuto extends CommandGroup {
	public OldCenterGearAuto() {
		addSequential(new DriveRobot(0.3, 0.0, 1200));
		addSequential(new InPlaceTurnToVisionAngle());
		addSequential(new ForeverDriveToPeg(0.3, 0.0));
		addSequential(new WaitCommand(Robot.getInstance().getGearSubsystem().getAutoCloseBackoffTime()));
		addSequential(new DriveRobot(-0.3, 0.0, 2000));
	}
}
