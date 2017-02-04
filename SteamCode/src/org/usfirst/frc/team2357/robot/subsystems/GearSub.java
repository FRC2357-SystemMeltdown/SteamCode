package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSub extends Subsystem {
	
	private final double leftGearSetPoint = 0;
	private final double rightGearSetPoint = 0;
	private final double leftGearReturnPoint = 0;
	private final double rightGearReturnPoint = 0;
	
	private Servo leftServo = new Servo(RobotMap.leftGearServo);
	private Servo rightServo = new Servo(RobotMap.rightGearServo);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void gearOut()
	{
		leftServo.set(leftGearSetPoint);
		rightServo.set(rightGearSetPoint);
	}
	
	public void gearIn() {
		// TODO Auto-generated method stub
		leftServo.set(leftGearReturnPoint);
		rightServo.set(rightGearReturnPoint);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

