package team696.frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team696.frc.lib.TalonFactory;
import team696.frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private static Shooter m_Shooter;

  private TalonFactory _LeftShooter;
  private TalonFactory _RightShooter;

  private VelocityVoltage _VelocityControllerL;
  private VelocityVoltage _VelocityControllerR;

  private BangBangController _BangBangController;
  /** Creates a new Shooter. */
  private Shooter() {
    _LeftShooter = new TalonFactory(5, Constants.configs.shooter.left, "Shooter Left");
    _RightShooter = new TalonFactory(3, Constants.configs.shooter.right, "Shooter Right");

    _VelocityControllerL = new VelocityVoltage(0);
    _VelocityControllerR = new VelocityVoltage(0);

    _BangBangController = new BangBangController(100);

    this.setDefaultCommand(this.spinShooter(0));
  }

  public static Shooter get() {
    if (m_Shooter == null) {
      m_Shooter = new Shooter();
    }

    return m_Shooter;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public boolean upToSpeed(double top, double tolerance) {
      if (_LeftShooter.getVelocity() * 60 < top - tolerance ) return false;
      if (_RightShooter.getVelocity() * 60 < top - tolerance ) return false;

      return true;
  }

  public boolean upToSpeed(Constants.shooter.state desired, double tolerance) {
      if (_LeftShooter.getVelocity()  * 60 < desired.speed_l - tolerance || _LeftShooter.getVelocity()  * 60 > desired.speed_l + tolerance) return false;
      if (_RightShooter.getVelocity() * 60 < desired.speed_r - tolerance || _RightShooter.getVelocity() * 60 > desired.speed_r + tolerance) return false;

      return true;
  }

  public void setShooter(Constants.shooter.state desired) {
    _LeftShooter.setControl(_VelocityControllerL.withVelocity(desired.speed_l / 60.0));
    _RightShooter.setControl(_VelocityControllerR.withVelocity(desired.speed_l / 60.0));
  }

  public void setShooter(double l) {
    _LeftShooter.setControl(_VelocityControllerL.withVelocity(l/60.0));
    _RightShooter.setControl(_VelocityControllerR.withVelocity(l/60.0));
  }

  public void setBangShooter(double speed) {
    _LeftShooter.PercentOutput(_BangBangController.calculate(_LeftShooter.getVelocity() * 60, speed));
    _RightShooter.PercentOutput(_BangBangController.calculate(_RightShooter.getVelocity() * 60, speed));
  }

  public void stop() {
    _LeftShooter.stop();
    _RightShooter.stop();
  }

  public void setShooterPercent(double l) {
  _LeftShooter.PercentOutput(l);_RightShooter.PercentOutput(l);
  }

  public Command spinShooter(double speed) {
    return this.runEnd(()->  setShooterPercent(speed), this::stop);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("Left Velocity", ()->_LeftShooter.getVelocity() * 60, null);
    builder.addDoubleProperty("Right Velocity", ()->_RightShooter.getVelocity() * 60, null);

     builder.addDoubleProperty("Left Position", ()->_LeftShooter.getPosition(), null);
    builder.addDoubleProperty("Right Position", ()->_RightShooter.getPosition(), null);
  }
}