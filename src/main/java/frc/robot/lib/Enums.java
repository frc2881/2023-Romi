package frc.robot.lib;

public final class Enums {

  public static enum Mode 
  {
    DISABLED("DISABLED"), 
    AUTONOMOUS("AUTONOMOUS"), 
    TELEOP("TELEOP"), 
    TEST("TEST");
  
    private String mode;
    Mode(String mode) { this.mode = mode; }
    public String getMode() { return mode; }
  }

}
