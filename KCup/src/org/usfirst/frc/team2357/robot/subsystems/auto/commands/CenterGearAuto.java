package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns.RadiusTurnToFixedAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives forward and scores on the center peg. If sprinting is on, it then
 * drives around the feeder side and up the field toward our feeder.
 */
public class CenterGearAuto extends CommandGroup {

	/**
	 * The command action only varies by red/blue if sprinting is on.
	 * 
	 * @param red
	 *            true if on the red alliance and false if on blue.
	 */
	public CenterGearAuto(boolean red) {
		addSequential(new DriveRobot(0.3, 0.0, 1000));

		addSequential(new ScoreGearStateMachineCommand());

		if (Robot.getInstance().getAutonomousSubsystem().isAutoSprint()) {
			addSequential(new RadiusTurnToFixedAngle(red ? -90.0 : 90.0, 24.0, -0.4, 3.0));
			addSequential(new RadiusTurnToFixedAngle(red ? 90.0 : -90.0, 216.0, -0.4, 3.0));
			addSequential(new DriveRobot(0.4, 0.0, 3000));
		}
	}
}
