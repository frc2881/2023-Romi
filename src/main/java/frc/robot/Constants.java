// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

public final class Constants {

  public static final class Telemetry {
    public static final boolean kEnableAllTelemetry = false;
  }
  
  public static final class Drive {
    public static final int kLeftMotorChannel = 0;
    public static final int kRightMotorChannel = 1;
    public static final int kLeftEncoderChannelA = 4;
    public static final int kLeftEncoderChannelB = 5;
    public static final int kRightEncoderChannelA = 6;
    public static final int kRightEncoderChannelB = 7;
    public static final double kCountsPerRevolution = 1440.0;
    public static final double kWheelDiameterInch = 2.75591; // 70 mm
  }
}
