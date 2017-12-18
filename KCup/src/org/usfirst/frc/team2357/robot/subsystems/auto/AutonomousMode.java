package org.usfirst.frc.team2357.robot.subsystems.auto;

import java.util.function.Supplier;

import org.usfirst.frc.team2357.robot.subsystems.auto.commands.BoilersideGearAuto;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.CenterGearAutoStateMachine;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.DriveBaseline;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.FeedersideGearAuto;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.OldCenterGearAuto;
import org.usfirst.frc.team2357.robot.subsystems.auto.commands.RedFeedersideGearAutoStateMachine;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This enumeration defines the autonomous modes available in the system. Each
 * enum value is constructed with a supplier for its {@link Command}. This is
 * done so that only the one command that will be run needs to be constructed.
 */
enum AutonomousMode {
	DriveBaseline(() -> new DriveBaseline()),
	CenterGearAutoRed(() -> new CenterGearAutoStateMachine()),
	CenterGearAutoBlue(() -> new CenterGearAutoStateMachine()),
	FeedersideGearAutoRed(() -> new RedFeedersideGearAutoStateMachine()),
	FeedersideGearAutoBlue(() -> new FeedersideGearAuto(false)),
	BoilersideGearAutoRed(() -> new BoilersideGearAuto(true)),
	BoilersideGearAutoBlue(() -> new BoilersideGearAuto(false)),
	OldCenterGearAuto(() -> new OldCenterGearAuto());

	private final Supplier<Command> autoCommandSupplier;
	private Command autoCommand;

	private AutonomousMode(Supplier<Command> autoCommandSupplier) {
		this.autoCommandSupplier = autoCommandSupplier;
	}

	/**
	 * Access the default {@link AutonomousMode}.
	 * 
	 * @return the default {@link AutonomousMode}.
	 */
	public static AutonomousMode getDefault() {
		return AutonomousMode.DriveBaseline;
	}

	/**
	 * Returns the autonomous {@link Command} for the reference value of this
	 * enumeration.
	 * 
	 * @return the autonomous {@link Command} for one enumeration value.
	 */
	public synchronized Command getAutonomousCommand() {
		if (this.autoCommand == null) {
			this.autoCommand = this.autoCommandSupplier.get();
		}
		return this.autoCommand;
	}
}
