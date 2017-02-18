package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.Robot;
import org.usfirst.frc.team2357.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSub extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private VictorSP lMotor = new VictorSP(RobotMap.leftWinchController);
	private VictorSP rMotor = new VictorSP(RobotMap.rightWinchController);
	
	private float motorRate = 1.0f;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public ClimberSub() {
		// TODO Auto-generated constructor stub
    	lMotor.setInverted(true);
	}
    
    public void windUp()
    {
    	lMotor.set(motorRate);
    	rMotor.set(motorRate);
    }
    
    public void windDown()
    {
    	lMotor.set(-motorRate);
    	rMotor.set(-motorRate);
    }
    
    public void stop()
    {
    	lMotor.set(0.0f);
    	rMotor.set(0.0f);
    }
}

