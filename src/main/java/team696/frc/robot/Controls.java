package team696.frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public final class Controls {

    public static final class Controller {
        public static final CommandPS4Controller controller = new CommandPS4Controller(5);

        public static final DoubleSupplier leftJoyY =  ()->controller.getRawAxis(1);
        public static final DoubleSupplier leftJoyX =  ()->-controller.getRawAxis(0);
        public static final DoubleSupplier rightJoyX = ()->-controller.getRawAxis(2);

        public static final Trigger triangle = controller.triangle();
        public static final Trigger circle = controller.circle();
        public static final Trigger square = controller.square();
        public static final Trigger cross = controller.cross();
    }
}
