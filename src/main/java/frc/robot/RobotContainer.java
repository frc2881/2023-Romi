// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.auto.RunAutoCalibration;
import frc.robot.commands.auto.RunAutoDistance;
import frc.robot.commands.auto.RunAutoTime;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.ZeroHeading;
import frc.robot.subsystems.Drive;

import frc.robot.lib.Utils;

public class RobotContainer {
  private Drive m_drive = new Drive();

  private final XboxController m_driverController = new XboxController(Constants.Controllers.kDriverControllerPort);

  private final SendableChooser<Command> m_autonomousChooser = new SendableChooser<>();

  public boolean m_robotResetState = true;

  public RobotContainer() {
    setupDrive();
    setupControllers();
    setupAuto();
  }

  private void setupDrive() {
    m_drive.setDefaultCommand(
      new ArcadeDrive(
        m_drive, 
        () -> Utils.applyDeadband(-m_driverController.getRawAxis(1), Constants.Controllers.kDeadband), 
        () -> Utils.applyDeadband(m_driverController.getRawAxis(4), Constants.Controllers.kDeadband)
      )
    );
  }

  private void setupControllers() {
    new Trigger(m_driverController::getBackButton)
      .onTrue(new ZeroHeading(m_drive));
  }

  private void setupAuto() {
    m_autonomousChooser.setDefaultOption("None", null);
    m_autonomousChooser.addOption("Calibration", new RunAutoCalibration(m_drive));
    m_autonomousChooser.addOption("Distance", new RunAutoDistance(m_drive));
    m_autonomousChooser.addOption("Time", new RunAutoTime(m_drive));
    SmartDashboard.putData("Auto/Command", m_autonomousChooser);
  }

  public Command getAutonomousCommand() {
    return m_autonomousChooser.getSelected();
  }

  public void resetRobot() {
    if (m_robotResetState) {
      // call individual subsystem reset methods as needed
      m_robotResetState = false;
    }
  }

  public void robotShouldReset() {
    m_robotResetState = true;
  }
}
