package org.usfirst.frc.team2357.robot.subsystems.vision.commands;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.vision.VisionSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * When run, this command will toggle the vision targeting between peg and
 * feeder alignment.
 */
public class ToggleAlignToPegOrFeeder extends Command {
	/**
	 * The subsystem can and should be initialized in the constructor since the
	 * subsystems are the first thing created during robot initialization and we
	 * want to have the command requirements correct from the start.
	 */
	private final VisionSubsystem visionSubsystem;

	public ToggleAlignToPegOrFeeder() {
		this.visionSubsystem = Robot.getInstance().getVisionSubsystem();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Toggles the vision search between feeder and peg vision targets.
	 * </p>
	 */
	protected void execute() {
		if (this.visionSubsystem.searchingForFeeder()) {
			this.visionSubsystem.setAlignedToFeeder(false);
		} else {
			this.visionSubsystem.setAlignedToFeeder(true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * This command is done right away.
	 * </p>
	 */
	protected boolean isFinished() {
		return true;
	}
}
