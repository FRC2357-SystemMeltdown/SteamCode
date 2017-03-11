package org.usfirst.frc.team2357.robot.AutonomousModes;

import org.usfirst.frc.team2357.robot.commands.DriveDistance;
import org.usfirst.frc.team2357.robot.commands.DriveRobot;
import org.usfirst.frc.team2357.robot.commands.DriveUntilPegged;
import org.usfirst.frc.team2357.robot.commands.ForeverDriveToPeg;
import org.usfirst.frc.team2357.robot.commands.TurnToFixedAngle;
import org.usfirst.frc.team2357.robot.commands.TurnToVisionAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BoilersideGearAuto extends CommandGroup {

    public BoilersideGearAuto() {
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
    	addSequential(new DriveRobot(0.5, 0.0, 3000)); //addSequential(new DriveDistance(87.09));
    	addSequential(new TurnToFixedAngle(45.0, false));
    	//addSequential(new TurnToFixedAngle(30.0, false));
    	//addSequential(new TurnToVisionAngle());
    	addSequential(new DriveRobot(0.5, 0.0, 800)); //addSequential(new DriveDistance(6)); //TODO Requires Tuning
    	addSequential(new TurnToVisionAngle());
    	addSequential(new ForeverDriveToPeg(0.5, 0.0)); //addSequential(new DriveUntilPegged(36));
    	addSequential(new WaitCommand(1.0));
    	addSequential(new DriveRobot(-0.5, 0.0, 2000)); //addSequential(new DriveDistance(-48));
    	//addSequential(new TurnToFixedAngle(-30.0, false));
    	addSequential(new TurnToFixedAngle(-45.0, false));
    	addSequential(new DriveRobot(0.5, 0.0, 1000)); //addSequential(new DriveDistance(60));
    	
    }
}
