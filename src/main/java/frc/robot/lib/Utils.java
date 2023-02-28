// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.lib;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public final class Utils {
  
  /* Returns an initialized 2d pose set to 0,0,0 */
  public static Pose2d getDefaultPose() {
    return new Pose2d(0, 0, Rotation2d.fromDegrees(0));
  }

  /* Returns a controller input value with deadband constant applied */
  public static double applyDeadband(double input, double deadband) {
    return (Math.abs(input) < deadband) ? 0.0 : Math.copySign((Math.abs(input) - deadband) / (1.0 - deadband), input);
  }
}    