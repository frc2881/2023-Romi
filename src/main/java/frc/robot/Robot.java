// Copyright (c) 2023 FRC Team 2881 - The Lady Cans
//
// Open Source Software; you can modify and/or share it under the terms of BSD
// license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.lib.DataLog;
import frc.robot.lib.Telemetry;

public class Robot extends TimedRobot {
  private static Robot m_robotInstance;
  private RobotContainer m_robotContainer;
  private Command m_autonomousCommand;

  @Override
  public void robotInit() {
    m_robotInstance = this;
    DataLog.start();
    Telemetry.start();
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
  public void disabledInit() {
    DataLog.mode(Robot.Mode.DISABLED);
    m_robotContainer.robotShouldReset();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    DataLog.mode(Robot.Mode.AUTO);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    //m_robotContainer.resetRobot();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    DataLog.mode(Robot.Mode.TELEOP);
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    if (!isCompetitionMode()) {
      m_robotContainer.resetRobot();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    DataLog.mode(Robot.Mode.TEST);
    CommandScheduler.getInstance().cancelAll();
    m_robotContainer.resetRobot();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function provides static access to create a custom periodic function in the current robot instance. */
  public static void addCustomPeriodic(Runnable callback, double periodSeconds) {
    m_robotInstance.addPeriodic(callback, periodSeconds, 0.333);
  }

  public static boolean isCompetitionMode() {
    // In Practice mode and in a real competition getMatchTime() returns time left in this part of the match. Otherwise it just returns -1.0.
    return DriverStation.getMatchTime() != -1;
  }

  public static enum Mode 
  {
    DISABLED("DISABLED"), 
    AUTO("AUTO"), 
    TELEOP("TELEOP"), 
    TEST("TEST");
  
    private String mode;
    Mode(String mode) { this.mode = mode; }
    public String getMode() { return mode; }
  }

  public static enum Status 
  {
    DISABLED("DISABLED"), 
    ENABLED("ENABLED"),
    ESTOPPED("ESTOPPED");
  
    private String status;
    Status(String status) { this.status = status; }
    public String gertStatus() { return status; }
  }
}
