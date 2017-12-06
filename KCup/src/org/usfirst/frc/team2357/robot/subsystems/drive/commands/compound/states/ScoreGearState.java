package org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.states;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateCommand;
import edu.wpi.first.wpilibj.command.StateWaitCommand;

/**
 * The state machine for {@link ScoreGearStateMachineCommand}. Note the absence of an
 * in place turn and the use of a smarter gyro guided peg approach.
 */
public enum ScoreGearState implements State {
	DRIVE_TO_SCORE(null, new StateDriveUntilPegged(0.3, 3000.0)),
	BACK_OFF_AFTER_MISS(DRIVE_TO_SCORE, new DriveRobot(-0.3, 0.0, 1000)),
	BACK_OFF_AFTER_SCORE(null, new DriveRobot(-0.3, 0.0, 1000)),
	POST_SCORE_PAUSE(
			BACK_OFF_AFTER_SCORE,
			new StateWaitCommand(Robot.getInstance().getGearSubsystem().getAutoCloseBackoffTime() - 1.0));

	private final ScoreGearState nextState;
	private final StateCommand commandToRun;

	private ScoreGearState(ScoreGearState nextState, StateCommand commandToRun) {
		this.nextState = nextState;
		this.commandToRun = commandToRun;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public State getNextState() {
		return this.nextState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateCommand getCommandToRun() {
		return this.commandToRun;
	}
}
