package org.usfirst.frc.team2357.robot.subsystems;

import org.usfirst.frc.team2357.robot.RobotMap;
import org.usfirst.frc.team2357.robot.commands.ArcadeDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSub extends Subsystem {
	private CANTalon leftDrive = new CANTalon(RobotMap.LEFT_DRIVE_CAN);
	private CANTalon rightDrive = new CANTalon(RobotMap.RIGHT_DRIVE_CAN);
	private RobotDrive robotDrive = new RobotDrive(leftDrive, rightDrive);
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ArcadeDriveCommand());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
    	robotDrive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void stop() {
    	arcadeDrive(0.0, 0.0);
    }
}

