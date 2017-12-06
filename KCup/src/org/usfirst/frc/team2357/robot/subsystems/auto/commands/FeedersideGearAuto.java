package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns.RadiusTurnToFixedAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to score on the feeder side peg. If sprinting is on, it then drives up
 * field toward our feeder.
 */
public class FeedersideGearAuto extends CommandGroup {

	/**
	 * The command action varies by red/blue to turn the proper direction to
	 * score and to sprint in the proper direction.
	 * 
	 * @param red
	 *            true if on the red alliance and false if on blue.
	 */
	public FeedersideGearAuto(boolean red) {
		addSequential(new DriveRobot(0.3, 0.0, 1000));
		addSequential(new RadiusTurnToFixedAngle(red ? 60.0 : -60.0, 36.0, 0.4, 3.0));

		addSequential(new ScoreGearStateMachineCommand());

		if (Robot.getInstance().getAutonomousSubsystem().isAutoSprint()) {
			addSequential(new RadiusTurnToFixedAngle(red ? -60.0 : 60.0, 36.0, -0.4, 3.0));
			addSequential(new DriveRobot(0.4, 0.0, 3000));
		}
	}
}
