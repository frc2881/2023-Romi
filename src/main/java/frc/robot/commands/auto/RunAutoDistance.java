// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.subsystems.Drive;
import frc.robot.utils.Helpers;

public class RunAutoDistance extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive based on distance. This will drive out for a specified distance,
   * turn around and drive back.
   *
   * @param drive The drivetrain subsystem on which this command will run
   */
  public RunAutoDistance(Drive drive) {
    drive.resetOdometry(Helpers.getDefaultPose());
    
    addCommands(
      new DriveDistance(-0.5, 0.5, drive),
      new TurnDegrees(-0.5, 180, drive),
      new DriveDistance(-0.5, 0.5, drive),
      new TurnDegrees(0.5, 180, drive)
    );

    addRequirements(drive);
  }
}
