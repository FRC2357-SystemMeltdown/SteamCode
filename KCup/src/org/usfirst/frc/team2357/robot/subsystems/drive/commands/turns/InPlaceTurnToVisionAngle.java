package org.usfirst.frc.team2357.robot.subsystems.drive.commands.turns;

import org.usfirst.frc.team2357.robot.Robot;

/**
 *
 */
public class InPlaceTurnToVisionAngle extends AbstractInPlaceTurnToAngle {
	public InPlaceTurnToVisionAngle() {
		super(0.0);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		this.targetAngle = Robot.getInstance().getVisionSubsystem().getTurnAng();
		super.initialize();
	}
}
