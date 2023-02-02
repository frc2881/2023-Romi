package frc.robot.lib;

public final class Enums {

  public static enum Mode 
  {
    DISABLED("DISABLED"), 
    AUTONOMOUS("AUTONOMOUS"), 
    TELEOP("TELEOP"), 
    TEST("TEST");
  
    Mode(String mode) { this.mode = mode; }
    private String mode;
    public String getMode() { return mode; }
  }

}
