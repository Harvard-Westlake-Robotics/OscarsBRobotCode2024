// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team696.frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team696.frc.lib.TalonFactory;
import team696.frc.robot.Constants;

public class Intake extends SubsystemBase {
  private static Intake m_Intake;

  private TalonFactory _serializer;
  private TalonFactory _serializerSlave;

  private DigitalInput _Beam;


  /** Creates a new Intake. */
  private Intake() {
    _serializer = new TalonFactory(2, Constants.configs.intake.serializer, "Intake Serializer");
    _serializerSlave = new TalonFactory(0, Constants.configs.intake.slave, "Intake Serializer Slave");

    _serializerSlave.Follow(_serializer, true);
  
    _Beam = new DigitalInput(9);
  }

  public static Intake get(){
    if (m_Intake == null) {
      m_Intake = new Intake();
    }

    return m_Intake;
  }

  public void stop() {
    _serializer.stop();
  }

  public void setSpeed(double speed) {
    _serializer.PercentOutput(speed);
  }

  public Command spin(double speed) {
    return this.runEnd(()->setSpeed(speed), this::stop);
  }

  public Command serialize(double speed) {
    return this.runEnd(()-> {
      if (!getBeamBreak()) {
        stop();
      } else {
        setSpeed(speed);
      }
    } ,this::stop);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  boolean getBeamBreak() {
    return _Beam.get();
  }

    @Override
  public void initSendable(SendableBuilder builder) {
    builder.addBooleanProperty("Beam Break", this::getBeamBreak, null);
  }
}
