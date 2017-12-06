package org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound;

import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.states.ScoreGearState;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateMachineCommand;

/**
 * Uses vision initialized and gyro guided "straight" drive to peg rather than
 * an in place turn and drive. This should be more accurate once we get it tuned
 * due to using the gyro to stay on target while moving. It should also be
 * faster. Finally, it also has retry after miss.
 */
public class ScoreGearStateMachineCommand extends StateMachineCommand<ScoreGearState> {
	public ScoreGearStateMachineCommand() {
		super(ScoreGearState.class);
	}

	@Override
	protected State getInitialState() {
		return ScoreGearState.DRIVE_TO_SCORE;
	}
}
