package team696.frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public final class Configs { 
    public final class Hood {
        public TalonFXConfiguration left;
        public TalonFXConfiguration right;
    }

    public final class Shooter {
        public TalonFXConfiguration left;
        public TalonFXConfiguration right;
        public TalonFXConfiguration serializer;
    }

    public final class Intake {
        public TalonFXConfiguration serializer;
        public TalonFXConfiguration slave;
    }

    public Hood hood = new Hood();
    public Shooter shooter = new Shooter();
    public Intake intake = new Intake();

    public Configs() {
        hood.left = new TalonFXConfiguration();
        hood.right = new TalonFXConfiguration();
    
        shooter.left = new TalonFXConfiguration();
        shooter.right = new TalonFXConfiguration();
        shooter.serializer = new TalonFXConfiguration();

        intake.serializer = new TalonFXConfiguration();
        intake.slave = new TalonFXConfiguration();

        hood.left.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        hood.left.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        hood.left.CurrentLimits.StatorCurrentLimitEnable = true;
        hood.left.CurrentLimits.StatorCurrentLimit = 60;
        hood.left.Slot0.kP = 5.; 
        hood.left.Slot0.kS = 0.6;
        hood.left.Slot0.kV = 0;
        hood.left.Slot0.kA = 0;
        hood.left.MotionMagic.MotionMagicCruiseVelocity = 100;
        hood.left.MotionMagic.MotionMagicAcceleration = 200;

        hood.right.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        hood.right.MotorOutput.NeutralMode = hood.left.MotorOutput.NeutralMode;
        hood.right.Slot0.kP = hood.left.Slot0.kP;
        hood.right.CurrentLimits.StatorCurrentLimitEnable = true;
        hood.right.CurrentLimits.StatorCurrentLimit = 60;
        hood.right.Slot0.kP = hood.left.Slot0.kP; 
        hood.right.Slot0.kS = hood.left.Slot0.kS;
        hood.right.Slot0.kV = hood.left.Slot0.kV;
        hood.right.Slot0.kA = hood.left.Slot0.kA;
        hood.right.MotionMagic.MotionMagicCruiseVelocity = hood.left.MotionMagic.MotionMagicCruiseVelocity;
        hood.right.MotionMagic.MotionMagicAcceleration = hood.left.MotionMagic.MotionMagicAcceleration;

        shooter.left.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        shooter.left.Slot0.kP = 0.35;
        shooter.left.Slot0.kV = 0.14;
        shooter.left.Slot0.kS = 0.14;
        shooter.left.CurrentLimits.StatorCurrentLimitEnable = true;
        shooter.left.CurrentLimits.StatorCurrentLimit = 80;
        shooter.left.Feedback.SensorToMechanismRatio = 30.0/18.0;

        shooter.left.Voltage.PeakForwardVoltage = 12.;
        shooter.left.Voltage.PeakReverseVoltage = -12.;

        shooter.right.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        shooter.right.Slot0.kP = shooter.left.Slot0.kP;
        shooter.right.Slot0.kV = shooter.left.Slot0.kV;
        shooter.right.Slot0.kS = shooter.left.Slot0.kS;
        shooter.right.CurrentLimits.StatorCurrentLimitEnable = true;
        shooter.right.CurrentLimits.StatorCurrentLimit = 80;
        shooter.right.Feedback.SensorToMechanismRatio = shooter.left.Feedback.SensorToMechanismRatio;

        shooter.right.Voltage.PeakForwardVoltage = 12.;
        shooter.right.Voltage.PeakReverseVoltage = -12.;

        intake.serializer.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        intake.serializer.CurrentLimits.StatorCurrentLimitEnable = true;
        intake.serializer.CurrentLimits.StatorCurrentLimit = 60;
        intake.serializer.Voltage.PeakForwardVoltage = 12.;
        intake.serializer.Voltage.PeakReverseVoltage = -12.;

        intake.slave.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        intake.slave.CurrentLimits.StatorCurrentLimitEnable = true;
        intake.slave.CurrentLimits.StatorCurrentLimit = 60;
        intake.slave.Voltage.PeakForwardVoltage = 12.;
        intake.slave.Voltage.PeakReverseVoltage = -12.;        

        shooter.serializer.Slot0.kP = 12.;
    }
}