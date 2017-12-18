package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.auto.commands.RedFeedersideGearAutoStateMachine.RedFeederSideState;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns.RadiusTurnToFixedAngle;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateCommand;
import edu.wpi.first.wpilibj.command.StateMachineCommand;

/**
 * Drives and scores on the red feederside peg. If sprinting is on, it then
 * drives around the feeder side and up the field toward our feeder.
 */
public class RedFeedersideGearAutoStateMachine extends StateMachineCommand<RedFeederSideState> {

	public RedFeedersideGearAutoStateMachine() {
		super(RedFeederSideState.class);
	}

	@Override
	protected State getInitialState() {
		return RedFeederSideState.INITIAL_DRIVE;
	}

	public enum RedFeederSideState implements State {
		SPRINT(null, new RedFeedersideSprint()),
		SCORE(SPRINT, new ScoreGearStateMachineCommand()),
		TURN(SCORE, new RadiusTurnToFixedAngle(60.0, 36.0, 0.4, 2.0)),
		INITIAL_DRIVE(TURN, new DriveRobot(0.3, 0.0, 1000));

		private final RedFeederSideState nextState;
		private final StateCommand commandToRun;

		private RedFeederSideState(RedFeederSideState nextState, StateCommand commandToRun) {
			this.nextState = nextState;
			this.commandToRun = commandToRun;
		}

		@Override
		public State getNextState() {
			return this.nextState;
		}

		@Override
		public StateCommand getCommandToRun() {
			return this.commandToRun;
		}

	}
}
