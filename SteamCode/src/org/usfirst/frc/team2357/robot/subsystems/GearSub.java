package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.PingGearBinAuto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSub extends Subsystem {

	private DigitalInput limitSwitch1 = new DigitalInput(0);
	private DigitalInput limitSwitch2 = new DigitalInput(1);

	private DoorPosition doorPosition = DoorPosition.CLOSED;

	public enum DoorPosition {
		CLOSED, OPEN;
	}

	public GearSub() {
	}

	public void ping() {
		if(doorPosition == DoorPosition.CLOSED){
			Robot.INSTANCE.pneumaticSubsystem.gearSolenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			Robot.INSTANCE.pneumaticSubsystem.gearSolenoid.set(DoubleSolenoid.Value.kReverse);
		}
			
	}

	public DoorPosition getDoorPosition() {
		return this.doorPosition == null ? DoorPosition.CLOSED : this.doorPosition;
	}

	public void setDoorPosition(DoorPosition doorPosition) {
		this.doorPosition = doorPosition;
	}

	public boolean isPegged() {
		return (this.limitSwitch1.get() || this.limitSwitch2.get());
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new PingGearBinAuto());
	}
}
