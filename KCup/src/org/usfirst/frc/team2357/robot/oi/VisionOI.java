package org.usfirst.frc.team2357.robot.oi;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.vision.commands.ToggleAlignToPegOrFeeder;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the vision
 * system.
 */
public class VisionOI {
	private final OI oi;

	/**
	 * Note that we have to get the {@link OI} as a parameter rather than from
	 * the robot since the subsystem OI objects are created during {@link OI}
	 * construction and the {@link Robot} will not yet have the reference.
	 * 
	 * @param oi
	 *            the one and only OI instance for the robot.
	 */
	public VisionOI(OI oi) {
		this.oi = oi;
		this.oi.coX.whenPressed(new ToggleAlignToPegOrFeeder());
	}
}
