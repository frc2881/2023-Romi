package frc.robot.utils;

public final class Helpers {
  
  public static double applyDeadband(double input) {
    if (Math.abs(input) < 0.1) {
      return 0.0;
    } else {
      return Math.copySign((Math.abs(input) - 0.1) / 0.9, input);
    }
  }

}
