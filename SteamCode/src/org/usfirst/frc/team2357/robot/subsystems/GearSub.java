package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.PingGearBinAuto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSub extends Subsystem {
	private Servo leftServo = new Servo(RobotMap.leftGearServo);
	private Servo rightServo = new Servo(RobotMap.rightGearServo);

	private DigitalInput limitSwitch1 = new DigitalInput(0);
	private DigitalInput limitSwitch2 = new DigitalInput(1);

	private DoorPosition doorPosition = DoorPosition.CLOSED;

	public enum DoorPosition {
		CLOSED(1.0, 0.0), OPEN(0.5, 0.5);

		private double leftSetPoint;
		private double rightSetPoint;

		private DoorPosition(double leftSetPoint, double rightSetPoint) {
			this.leftSetPoint = leftSetPoint;
			this.rightSetPoint = rightSetPoint;
		}

		public double getLeftSetPoint() {
			return this.leftSetPoint;
		}

		public double getRightSetPoint() {
			return this.rightSetPoint;
		}
	}

	public GearSub() {
	}

	public void ping() {
		this.leftServo.set(this.getDoorPosition().getLeftSetPoint());
		this.rightServo.set(this.getDoorPosition().getRightSetPoint());
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
