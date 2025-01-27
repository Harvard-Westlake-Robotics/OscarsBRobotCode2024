package team696.frc.lib.Swerve;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import team696.frc.robot.Constants.Motors;

public class SwerveConstants {
		public static final double drivekS = (0.667 / 12); 
		public static final double drivekV = (2.44 / 12);
		public static final double drivekA = (0.27 / 12);

		public static final double driveGearRatio = (5.36 / 1.0); // L3
		public static final double angleGearRatio = (150.0/7.0 / 1.0); 

		public static final double massKgs = Units.lbsToKilograms(70);

		public static final double wheelX = Units.inchesToMeters(13.0);
		public static final double wheelY = Units.inchesToMeters(13.0);
		public static final double wheelDiameter = Units.inchesToMeters(3.85);
		public static final double wheelCircumference = wheelDiameter * Math.PI;

		public static final double theoreticalMaxSpeed = Motors.Kraken.freeSpinRPM / 60 / driveGearRatio * wheelCircumference; // 5.13 mps way more resonable
		public static final double theoreticalMaxAcceleration = (4 * Motors.Kraken.stallTorqueNm * driveGearRatio) / (massKgs * wheelDiameter / 2);  // 66 mps^2 wtf
		public static final double maxSpeed = theoreticalMaxSpeed * 0.9; //MPS
		public static final double maxAngularVelocity = 8; //MPS^2

		public static final Translation2d[] modPositions = {
			new Translation2d(wheelX / 2.0, wheelY / 2.0), // FL
			new Translation2d(wheelX / 2.0, -wheelY / 2.0), // FR
			new Translation2d(-wheelX / 2.0, wheelY / 2.0), // BL
			new Translation2d(-wheelX / 2.0, -wheelY / 2.0) // BR  
		};

        public static final double driveBaseRadM = Math.sqrt(Math.pow(wheelX / 2, 2) + Math.pow(wheelY / 2, 2));
}
