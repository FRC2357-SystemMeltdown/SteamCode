package org.usfirst.frc.team2357.robot.triggers;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.commands.PingGearBinManual;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManuallySwingDoorTrigger extends Trigger {
	private final Button triggerButton;

	public ManuallySwingDoorTrigger(Button triggerButton) {
		this.triggerButton = triggerButton;
	}

	public boolean get() {
		// Do not use instanceof because of the shape of the command hierarchy
		Command currentGearCommand = Robot.INSTANCE.gearSubsystem.getCurrentCommand();
		return (currentGearCommand != null) && (currentGearCommand.getClass() == PingGearBinManual.class)
				&& (this.triggerButton.get());
	}
}
