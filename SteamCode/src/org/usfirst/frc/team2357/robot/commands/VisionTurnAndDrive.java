package org.usfirst.frc.team2357.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class VisionTurnAndDrive extends CommandGroup {

    public VisionTurnAndDrive() {
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
    	addSequential(new TurnToVisionAngle());
    	addSequential(new ForeverDriveToPeg(0.48, 0.0)); //addSequential(new DriveUntilPegged(36));
    	addSequential(new WaitCommand(1.0));
    	addSequential(new DriveRobot(-0.5, 0.0, 2000));
    }
}
