// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public final class Helpers {
  
  public static Pose2d getDefaultPose() {
    return new Pose2d(0, 0, Rotation2d.fromDegrees(0));
  }

  public static double applyDeadband(double input) {
    return (Math.abs(input) < 0.1) ? 0.0 : Math.copySign((Math.abs(input) - 0.1) / 0.9, input);
  }
}
