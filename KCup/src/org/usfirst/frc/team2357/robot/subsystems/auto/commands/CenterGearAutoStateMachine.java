package org.usfirst.frc.team2357.robot.subsystems.auto.commands;

import org.usfirst.frc.team2357.robot.subsystems.auto.commands.CenterGearAutoStateMachine.CenterState;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.ScoreGearStateMachineCommand;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobot;

import edu.wpi.first.wpilibj.command.State;
import edu.wpi.first.wpilibj.command.StateCommand;
import edu.wpi.first.wpilibj.command.StateMachineCommand;

/**
 * Drives forward and scores on the center peg.
 */
public class CenterGearAutoStateMachine extends StateMachineCommand<CenterState> {

	public CenterGearAutoStateMachine() {
		super(CenterState.class);
	}
	

	@Override
	protected State getInitialState() {
		return CenterState.INITIAL_DRIVE;
	}
	
	public enum CenterState implements State {
		SCORE(null, new ScoreGearStateMachineCommand()),
		INITIAL_DRIVE(SCORE, new DriveRobot(0.3, 0.0, 1000));
		
		private final CenterState nextState;
		private final StateCommand commandToRun;
		
		private CenterState(CenterState nextState, StateCommand commandToRun) {
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
