package org.usfirst.frc.team2357.robot.triggers;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;


/**
 *
 */
public class PressurePlateTrigger extends Trigger {

    public boolean get() {
        return Robot.INSTANCE.gearSubsystem.isPegged() ;
    }
}
