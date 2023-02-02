// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.hal.SimDouble;

public class RomiGyro implements Gyro {
  private SimDouble m_simRateX;
  private SimDouble m_simRateY;
  private SimDouble m_simRateZ;
  private SimDouble m_simAngleX;
  private SimDouble m_simAngleY;
  private SimDouble m_simAngleZ;

  private double m_angleXOffset;
  private double m_angleYOffset;
  private double m_angleZOffset;

  // Needed for interface requirements
  private SimDevice m_gyroSimDevice;

  public RomiGyro() {
    m_gyroSimDevice = SimDevice.create("Gyro:RomiGyro");
    if (m_gyroSimDevice != null) {
      m_gyroSimDevice.createBoolean("init", Direction.kOutput, true);
      m_simRateX = m_gyroSimDevice.createDouble("rate_x", Direction.kInput, 0.0);
      m_simRateY = m_gyroSimDevice.createDouble("rate_y", Direction.kInput, 0.0);
      m_simRateZ = m_gyroSimDevice.createDouble("rate_z", Direction.kInput, 0.0);

      m_simAngleX = m_gyroSimDevice.createDouble("angle_x", Direction.kInput, 0.0);
      m_simAngleY = m_gyroSimDevice.createDouble("angle_y", Direction.kInput, 0.0);
      m_simAngleZ = m_gyroSimDevice.createDouble("angle_z", Direction.kInput, 0.0);
    }
  }

  /**
   * Get the rate of turn in degrees-per-second around the X-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateX() {
    return m_simRateX != null ? m_simRateX.get() : 0.0;
  }

  /**
   * Get the rate of turn in degrees-per-second around the Y-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateY() {
    return m_simRateY != null ? m_simRateY.get() : 0.0;
  }

  /**
   * Get the rate of turn in degrees-per-second around the Z-axis.
   *
   * @return rate of turn in degrees-per-second
   */
  public double getRateZ() {
    return m_simRateZ != null ? m_simRateZ.get() : 0.0;
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around X-axis in degrees
   */
  public double getAngleX() {
    return m_simAngleX != null ? m_simAngleX.get() - m_angleXOffset : 0.0;
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around Y-axis in degrees
   */
  public double getAngleY() {
    return m_simAngleY != null ? m_simAngleY.get() - m_angleYOffset : 0.0;
  }

  /**
   * Get the currently reported angle around the Z-axis.
   *
   * @return current angle around Z-axis in degrees
   */
  public double getAngleZ() {
    return m_simAngleZ != null ? m_simAngleZ.get() - m_angleZOffset : 0.0;
  }

  /** Reset the gyro angles to 0. */
  public void reset() {
    if (m_simAngleX != null) {
      m_angleXOffset = m_simAngleX.get();
      m_angleYOffset = m_simAngleY.get();
      m_angleZOffset = m_simAngleZ.get();
    }
  }

  // These methods added to satisfy the interface requirements
  @Override
  public void close() throws Exception {
    if (m_gyroSimDevice != null) {
      m_gyroSimDevice.close();
    }
  }

  @Override
  public void calibrate() {}

  @Override
  public double getAngle() {
    return getAngleZ();
  }

  @Override
  public double getRate() {
    return getRateZ();
  }
}