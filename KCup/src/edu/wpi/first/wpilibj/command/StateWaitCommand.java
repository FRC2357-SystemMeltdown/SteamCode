package edu.wpi.first.wpilibj.command;

public class StateWaitCommand extends StateCommand {
	public StateWaitCommand(double timeout) {
		super();
		super.setTimeout(timeout);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
}
