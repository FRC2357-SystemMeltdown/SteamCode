package org.usfirst.frc.team2357.robot.subsystems.drive.commands.compound.states;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.drive.commands.flexible.DriveRobotToVisionUntilPegged;

import edu.wpi.first.wpilibj.command.NextStateProvider;
import edu.wpi.first.wpilibj.command.State;

public class StateDriveUntilPegged extends DriveRobotToVisionUntilPegged implements NextStateProvider {

	public StateDriveUntilPegged(double moveValue, double msTime) {
		super(moveValue, msTime);
	}

	@Override
	public State getNextState() {
		return Robot.getInstance().getGearSubsystem().isPegged() ? ScoreGearState.POST_SCORE_PAUSE
				: ScoreGearState.BACK_OFF_AFTER_MISS;
	}
}
