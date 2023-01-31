// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.auto.AutonomousDistance;
import frc.robot.commands.auto.AutonomousTime;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.subsystems.Drive;
import frc.robot.utils.Helpers;
import frc.robot.utils.Log;

public class RobotContainer {
  private final XboxController m_driverController = new XboxController(0);
  private final SendableChooser<Command> m_autonomousChooser = new SendableChooser<>();
  private final Drive m_drive = new Drive();

  public RobotContainer() {
    setupDrive();
    setupControllers();
    setupAutonomous();
  }

  private void setupDrive() {
    m_drive.setDefaultCommand(
      new ArcadeDrive(
        m_drive, 
        () -> Helpers.applyDeadband(-m_driverController.getRawAxis(1)), 
        () -> Helpers.applyDeadband(m_driverController.getRawAxis(4))
      )
    );
  }

  private void setupAutonomous() {
    m_autonomousChooser.setDefaultOption("Distance", new AutonomousDistance(m_drive));
    m_autonomousChooser.addOption("Time", new AutonomousTime(m_drive));

    SmartDashboard.putData("AutonomousMode", m_autonomousChooser);
  }

  public Command getAutonomousCommand() {
    return m_autonomousChooser.getSelected();
  }

  private void setupControllers() {
    if (m_driverController.isConnected()) {
      // TODO: implement explicit command for automatic logging as opposed to instant command
      new JoystickButton(m_driverController, XboxController.Button.kA.value)
        .onTrue(new InstantCommand(() -> { Log.log("Driver controller A button pressed"); }));
    }
  }
}
