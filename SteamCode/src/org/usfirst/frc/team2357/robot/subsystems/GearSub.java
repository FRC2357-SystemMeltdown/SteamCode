package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.DispenseGear;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSub extends Subsystem {
	
	private final double leftGearSetPoint = 0.5;
	private final double rightGearSetPoint = 0.5;
	private final double leftGearReturnPoint = 1;
	private final double rightGearReturnPoint = 0;
	
	
	private Servo leftServo = new Servo(RobotMap.leftGearServo);
	private Servo rightServo = new Servo(RobotMap.rightGearServo);
	
	private DigitalInput limitSwitch1 = new DigitalInput(0);
	private DigitalInput limitSwitch2 = new DigitalInput(1);
	
	
	public GearSub() {
		// TODO Auto-generated constructor stub
		gearIn();
	}

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
	
	public boolean isPegged()
	{
		return (limitSwitch1.get() || limitSwitch2.get());
	}
	
	public boolean isLeftServoOnTarget()
	{
		return(leftServo.get() == leftServo.get());
	}
	
	public boolean isRightServoOnTarget()
	{
		return(rightServo.get() == rightServo.getRaw());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

