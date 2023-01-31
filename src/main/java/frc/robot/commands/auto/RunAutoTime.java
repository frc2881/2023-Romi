// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.drive.DriveTime;
import frc.robot.commands.drive.TurnTime;
import frc.robot.subsystems.Drive;
import frc.robot.utils.Helpers;

public class RunAutoTime extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive based on time. This will drive out for a period of time, turn
   * around for time (equivalent to time to turn around) and drive forward again. This should mimic
   * driving out, turning around and driving back.
   *
   * @param drive The drive subsystem on which this command will run
   */
  public RunAutoTime(Drive drive) {
    drive.resetOdometry(Helpers.getDefaultPose());
    
    addCommands(
      new DriveTime(-0.6, 2.0, drive),
      new TurnTime(-0.5, 1.3, drive),
      new DriveTime(-0.6, 2.0, drive),
      new TurnTime(0.5, 1.3, drive)
    );

    addRequirements(drive);
  }
}
