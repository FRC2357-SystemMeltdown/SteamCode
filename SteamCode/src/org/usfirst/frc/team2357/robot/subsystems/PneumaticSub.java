package org.usfirst.frc.team2357.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.usfirst.frc.team2357.robot.commands.pneumaticExtend;
/**
 *
 */
public class PneumaticSub extends Subsystem {
	 DoubleSolenoid solenoid1 = new DoubleSolenoid(1,0);
	 Compressor compressor = new Compressor(0);
	 PowerDistributionPanel PDP = new PowerDistributionPanel();
	 

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

