// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package team696.frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import team696.frc.robot.subsystems.Hood;
import team696.frc.robot.subsystems.Intake;
import team696.frc.robot.subsystems.Shooter;

public class shoot extends Command {
  /** Creates a new shoot. */
  public shoot() {
    addRequirements(Shooter.get(), Intake.get(), Hood.get());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Hood.get().setHood(7.);
    Shooter.get().setShooterPercent(0.8);

    if (Hood.get().atAngle(7., 0.5) && Shooter.get().upToSpeed(2000, 200)) {
      Intake.get().setSpeed(0.9);
    } else {
      Intake.get().stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Hood.get().stop();
    Intake.get().stop();
    Shooter.get().stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
