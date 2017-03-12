package org.usfirst.frc.team2357.robot.AutonomousModes;

import org.usfirst.frc.team2357.robot.commands.DriveDistance;
import org.usfirst.frc.team2357.robot.commands.DriveRobot;
import org.usfirst.frc.team2357.robot.commands.DriveUntilPegged;
import org.usfirst.frc.team2357.robot.commands.TurnToFixedAngle;
import org.usfirst.frc.team2357.robot.commands.TurnToVisionAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FeederSideAuto extends CommandGroup {

    public FeederSideAuto() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new DriveRobot(0.5, 0.0, 5000));//addSequential(new DriveDistance(36));
    	if(DriverStation.getInstance().getAlliance() == Alliance.Blue){
        	addSequential(new TurnToFixedAngle(-60.0));
    	} else {
        	addSequential(new TurnToFixedAngle(60.0));
    	}
    	addSequential(new TurnToVisionAngle());
    	addSequential(new DriveDistance(12));
    	addSequential(new TurnToVisionAngle());
    	addSequential(new DriveUntilPegged(24));
    }
}
