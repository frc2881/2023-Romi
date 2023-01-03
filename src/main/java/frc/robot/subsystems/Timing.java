package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Timing extends SubsystemBase {
  public Timing() {}

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Timing/RobotTime", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Timing/MatchTime", Math.floor(DriverStation.getMatchTime()));
  }
}
