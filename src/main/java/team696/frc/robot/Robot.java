package team696.frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import team696.frc.lib.Auto;
import team696.frc.lib.Dashboards.ShuffleDashboard;
import team696.frc.robot.commands.TeleopSwerve;
import team696.frc.robot.commands.shoot;
import team696.frc.robot.subsystems.Hood;
import team696.frc.robot.subsystems.Intake;
import team696.frc.robot.subsystems.Shooter;
import team696.frc.robot.subsystems.Swerve;

public class Robot extends LoggedRobot {
    private Command m_autonomousCommand;

    private PowerDistribution m_PDH;

    @Override
    public void robotInit() {
        ShuffleDashboard.initialize();

        Logger.recordMetadata("ProjectName", "2024OffSeason");
        Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
        Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
        Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
        Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
        Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);
        switch (BuildConstants.DIRTY) {
          case 0:
            Logger.recordMetadata("GitDirty", "All changes committed");
            break;
          case 1:
            Logger.recordMetadata("GitDirty", "Uncomitted changes");
            break;
          default:
            Logger.recordMetadata("GitDirty", "Unknown");
            break;
        }

        switch (Constants.Robot.detected) {
          case SIM:
            Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
            break;
          case COMP:
          case BETA:
          case UNKNOWN:
          default:
            Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
            //Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
            break;
        }
        m_PDH = new PowerDistribution(1, ModuleType.kRev); 
        
        Logger.start(); 

        DriverStation.silenceJoystickConnectionWarning(true);
    
        LiveWindow.disableAllTelemetry();
        RobotController.setEnabled3V3(false);
        RobotController.setEnabled5V(true);
        RobotController.setEnabled6V(false);

        m_PDH.setSwitchableChannel(true);
        
        SmartDashboard.putData(Swerve.get());
        SmartDashboard.putData(Intake.get());
        SmartDashboard.putData(Shooter.get());
        SmartDashboard.putData(Hood.get());

        Hood.get().setDefaultCommand(Hood.get().positionHood(0.1));

        Auto.Initialize(Swerve.get()
        );

        //configureBinds();
        //configureOperatorBinds();
        
        configureControllerBinds();
        //configureWebControlsBinds();
      }

  @Override
  public void robotPeriodic() {
      CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() { }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = Auto.Selected();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override 
  public void autonomousExit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() { }

  @Override
  public void simulationInit() { }

  @Override
  public void simulationPeriodic() { }

  @SuppressWarnings("unused") 
    private void configureBinds() {

    }

    @SuppressWarnings("unused") 
    private void configureOperatorBinds() {

    }

    @SuppressWarnings("unused") 
    private void configureControllerBinds() { 
      TeleopSwerve.config(Controls.Controller.leftJoyX, Controls.Controller.leftJoyY, Controls.Controller.rightJoyX, null, 0.02);
      Swerve.get().setDefaultCommand(new TeleopSwerve(()->Swerve.get().getAngleToSpeaker()));


      Controls.Controller.triangle.whileTrue(Intake.get().serialize(0.8));

      Controls.Controller.circle.whileTrue(Shooter.get().spinShooter(0.2));

      Controls.Controller.square.whileTrue(new shoot());
    
      Controls.Controller.cross.whileTrue(Hood.get().positionHood(6.));
    }
}
