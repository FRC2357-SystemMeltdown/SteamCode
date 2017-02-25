package org.usfirst.frc.team2357.robot.triggers;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.commands.PingGearBinManual;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class ManuallySwingDoorTrigger extends Trigger {
	private final Button triggerButton;

	public ManuallySwingDoorTrigger(Button triggerButton) {
		this.triggerButton = triggerButton;
	}

	public boolean get() {
		return (Robot.INSTANCE.gearSubsystem2.getCurrentCommand() instanceof PingGearBinManual)
				&& (this.triggerButton.get());
	}
}
