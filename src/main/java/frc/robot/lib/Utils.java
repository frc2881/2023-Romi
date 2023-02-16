// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.lib;

import java.util.Arrays;

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

  /**
   * Performs a low-pass filtering and smoothing operation on a collection of analog signal values and returns the calculated average.
   *
   * @param values contains the array of analog signal values captured over a time range
   * @param strength provides the amount of smoothing applied to the filter (1 = no smoothing applied)
   */
  public static double getAnalogSignalAverageWithFilter(double[] values, double strength) {
    double value = values[0];
    for (int i = 1, ic = values.length; i < ic; i += 1) {
      double __value = values[i];
      value += (__value - value) / strength;
      values[i] = value;
    }
    return Arrays.stream(values).average().orElse(Double.NaN);
  }

}    