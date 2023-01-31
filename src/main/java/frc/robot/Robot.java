// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.utils.Log;

public class Robot extends TimedRobot {
  private static Robot m_robotInstance;
  private RobotContainer m_robotContainer;
  private Command m_autonomousCommand;

  @Override
  public void robotInit() {
    m_robotInstance = this;
    setupLogging();
    setupTelemetry();
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    // Get selected routine from the SmartDashboard
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running which will
    // use the default command which is ArcadeDrive. If you want the autonomous
    // to continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  public static void addCustomPeriodic(Runnable callback, double periodSeconds) {
    m_robotInstance.addPeriodic(callback, periodSeconds);
  }

  private void setupLogging() {
    DataLogManager.start();
    DriverStation.startDataLog(DataLogManager.getLog());

    Log.start();

    CommandScheduler.getInstance().onCommandInitialize(command -> Log.init(command));
    CommandScheduler.getInstance().onCommandInterrupt(command -> Log.end(command, true));
    CommandScheduler.getInstance().onCommandFinish(command -> Log.end(command, false));
  }

  private void setupTelemetry() {
    if (Constants.Telemetry.kEnableAllTelemetry) {
      LiveWindow.enableAllTelemetry();
    }
    
    Robot.addCustomPeriodic(this::updateFPGATimestamp, 3);
    Robot.addCustomPeriodic(this::updateMatchTime, 0.2);
  }

  private void updateFPGATimestamp() {
    SmartDashboard.putNumber("Timing/FPGATimestamp", Timer.getFPGATimestamp());
  }

  private void updateMatchTime() {
    SmartDashboard.putNumber("Timing/MatchTime", Math.floor(Timer.getMatchTime()));
  }
}
