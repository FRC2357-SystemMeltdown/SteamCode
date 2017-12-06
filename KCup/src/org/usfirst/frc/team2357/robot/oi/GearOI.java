package org.usfirst.frc.team2357.robot.oi;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.subsystems.gear.commands.ToggleAutoDoorsEnabled;
import org.usfirst.frc.team2357.robot.subsystems.gear.commands.RequestManualDoorSwing;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the gear
 * bin.
 */
public class GearOI {
	private final OI oi;
	private boolean manualDoorSwingRequested = false;

	/**
	 * Note that we have to get the {@link OI} as a parameter rather than from
	 * the robot since the subsystem OI objects are created during {@link OI}
	 * construction and the {@link Robot} will not yet have the reference.
	 * 
	 * @param oi
	 *            the one and only OI instance for the robot.
	 */
	public GearOI(OI oi) {
		this.oi = oi;
		this.oi.coA.whenPressed(new RequestManualDoorSwing());
		this.oi.coSelect.whenPressed(new ToggleAutoDoorsEnabled());
	}

	/**
	 * Returns true if the drive team has requested a manual gear bin door
	 * swing. If true is returned, the gear subsystem will be in manual mode.
	 * Note that true will only be returned once per request.
	 * 
	 * @return true if the manual door swing button was pressed and false
	 *         otherwise.
	 */
	public boolean isManualDoorSwingRequested() {
		boolean requested = this.manualDoorSwingRequested;
		this.manualDoorSwingRequested = false;
		return requested;
	}

	/**
	 * Requests a manual door swing. The gear bin doors will be in manual mode
	 * after this method completes.
	 */
	public void requestManualDoorSwing() {
		this.manualDoorSwingRequested = true;
	}
}
