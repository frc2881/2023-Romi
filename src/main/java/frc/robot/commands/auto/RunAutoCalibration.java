// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.drive.DriveDistance;
import frc.robot.lib.Utils;
import frc.robot.subsystems.Drive;

public class RunAutoCalibration extends SequentialCommandGroup {
  /**
   * Creates a new Autonomous Drive to calibrate a distance traveled of 1 meter
   *
   * @param drive The drivetrain subsystem on which this command will run
   */
  public RunAutoCalibration(Drive drive) {
    drive.resetOdometry(Utils.getDefaultPose());
    
    addCommands(
      new DriveDistance(0.5, 1, drive)
    );

    addRequirements(drive);
  }
}
