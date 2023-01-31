// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.utils.RomiGyro;

public class Drive extends SubsystemBase {
  private final Spark m_leftMotor = new Spark(Constants.Drive.kLeftMotorChannel);
  private final Spark m_rightMotor = new Spark(Constants.Drive.kRightMotorChannel);
  private final Encoder m_leftEncoder = new Encoder(Constants.Drive.kLeftEncoderChannelA, Constants.Drive.kLeftEncoderChannelB);
  private final Encoder m_rightEncoder = new Encoder(Constants.Drive.kRightEncoderChannelA, Constants.Drive.kRightEncoderChannelB);
  private final DifferentialDrive m_diffDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final RomiGyro m_gyro = new RomiGyro();
  private final BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();
  private DifferentialDrivePoseEstimator m_poseEstimator;
  private final Field2d m_field2d = new Field2d();

  public Drive() {
    m_rightMotor.setInverted(true);

    m_leftEncoder.setDistancePerPulse((Math.PI * Constants.Drive.kWheelDiameterMeters) / Constants.Drive.kCountsPerRevolution);
    m_rightEncoder.setDistancePerPulse((Math.PI * Constants.Drive.kWheelDiameterMeters) / Constants.Drive.kCountsPerRevolution);

    resetEncoders();

    Pose2d initialPose = new Pose2d(0, 0, m_gyro.getRotation2d());    
    m_field2d.setRobotPose(initialPose);

    m_poseEstimator = new DifferentialDrivePoseEstimator(
      Constants.Drive.kDriveKinematics,
      m_gyro.getRotation2d(), 
      getLeftDistanceMeters(), 
      getRightDistanceMeters(), 
      initialPose,
      VecBuilder.fill(0.02, 0.02, 0.01),
      VecBuilder.fill(0.1, 0.1, 0.1)
    );

    SmartDashboard.putData("Field", m_field2d);
  }

  public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
    m_diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public void zeroHeading() {
    m_gyro.reset();
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    zeroHeading();
    m_poseEstimator.resetPosition(Rotation2d.fromDegrees(m_gyro.getAngle()), getAccelY(), getAccelX(), pose);
  }

  public int getLeftEncoderCount() {
    return m_leftEncoder.get();
  }

  public int getRightEncoderCount() {
    return m_rightEncoder.get();
  }

  public double getLeftDistanceMeters() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDistanceMeters() {
    return m_rightEncoder.getDistance();
  }

  public double getAverageDistanceMeters() {
    return (getLeftDistanceMeters() + getRightDistanceMeters()) / 2.0;
  }

  public double getAccelX() {
    return m_accelerometer.getX();
  }

  public double getAccelY() {
    return m_accelerometer.getY();
  }

  public double getAccelZ() {
    return m_accelerometer.getZ();
  }

  public double getGyroAngleX() {
    return m_gyro.getAngleX();
  }

  public double getGyroAngleY() {
    return m_gyro.getAngleY();
  }

  public double getGyroAngleZ() {
    return m_gyro.getAngleZ();
  }

  public Pose2d getPose() {
    return m_poseEstimator.getEstimatedPosition();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getRate(), m_rightEncoder.getRate());
  }

  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  public double getLeftEncoderRate() {
    return m_leftEncoder.getRate();
  }

  public double getRightEncoderRate() {
    return m_rightEncoder.getRate();
  }
  
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  @Override
  public void periodic() {
    m_poseEstimator.update(
      m_gyro.getRotation2d(),
      m_leftEncoder.getDistance(),
      m_rightEncoder.getDistance()
    );

    m_field2d.setRobotPose(m_poseEstimator.getEstimatedPosition());  
  }
}
