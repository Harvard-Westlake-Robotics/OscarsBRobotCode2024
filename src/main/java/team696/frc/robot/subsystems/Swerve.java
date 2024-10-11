package team696.frc.robot.subsystems;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import team696.frc.lib.Util;
import team696.frc.lib.Dashboards.ShuffleDashboard;
import team696.frc.lib.Swerve.SwerveDriveSubsystem;
import team696.frc.robot.Constants;

public class Swerve extends SwerveDriveSubsystem {
  private static Swerve m_Swerve;

  public static Swerve get() {
    if (m_Swerve == null) {
      m_Swerve = new Swerve();
    }
    return m_Swerve;
  }
  
  private Swerve() {

  }

  public Rotation2d getAngleToSpeaker() {
    if (Util.getAlliance() == Alliance.Red) 
      return getVelocityAdjustedAngleToPos(Constants.Field.RED.Speaker);

    return getVelocityAdjustedAngleToPos(Constants.Field.BLUE.Speaker);
  }

  public Rotation2d getAngleToCorner() {
    if (Util.getAlliance() == Alliance.Red) 
      return getVelocityAdjustedAngleToPos(Constants.Field.RED.Corner);

    return getVelocityAdjustedAngleToPos(Constants.Field.BLUE.Corner);
  }

  public Rotation2d getVelocityAdjustedAngleToPos(Translation2d position) {
    double dist = distTo(position);
    Translation2d adjustment = (new Translation2d(0, 1.0/11.0 * getRobotRelativeSpeeds().vyMetersPerSecond * dist)).rotateBy(angleTo(position)).plus(getPose().getTranslation()).minus(position);
    Rotation2d rot = Rotation2d.fromRadians(Math.atan2(adjustment.getY(), adjustment.getX()));

    return rot;
  }

  public double AngleDiffFromSpeakerDeg() {
    return getPose().getRotation().minus(getAngleToSpeaker()).getDegrees();
  }

  public double getDistToSpeaker() {
    if (Util.getAlliance() == Alliance.Red) 
        return distTo(Constants.Field.RED.Speaker);
   
    return distTo(Constants.Field.BLUE.Speaker);
  }

  public double getDistToCorner() {
    if (Util.getAlliance() == Alliance.Red) 
        return distTo(Constants.Field.RED.Corner);
    
    return distTo(Constants.Field.BLUE.Corner);
  }

  Pose2d oldPose = new Pose2d();

  @Override
  public void onUpdate() { 
    getState().publish();

    ShuffleDashboard.field.setRobotPose(getState().pose);
  }

  @Override 
  public void simulationPeriodic() { 
    this.addVisionMeasurement(new Pose2d(1,1,Rotation2d.fromDegrees(90)), Timer.getFPGATimestamp(), VecBuilder.fill(0.1,0.1,0.1)); // fake estimate to check for crashes
  }
  
  @Override
  public void initSendable(SendableBuilder builder) {
    if (Constants.DEBUG) {
      for (team696.frc.lib.Swerve.SwerveModule mod : _modules) {
        builder.addDoubleProperty("encoder" + mod.moduleNumber, ()->mod.getCANCoderAngle().getRotations(), null);
      }
      builder.addDoubleProperty("Gyro", ()->getYaw().getDegrees(), null);
      builder.addDoubleProperty("DistToSpeaker",()->getDistToSpeaker(),null);
    }
  }
}
