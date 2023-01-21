package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.*;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static java.lang.Thread.sleep;

public class NtClientTest {

    @Test
    public void sendToNetworkTablesTopic() throws Exception {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("TestTopic");
        try (DoublePublisher xPub = table.getDoubleTopic("X").publish()) {
            inst.setServer("localhost");
            inst.startClient3("test publisher");
            double x = 0.0;
            while (true) {
                sleep(1000);
                xPub.set(x);
                System.out.println("Sent x: " + x);
                x = x + 1.0;
            }
        }
    }

    @Test
    public void readFromSmartDashboardTopic() throws Exception {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("TestTopic");

        try (DoubleSubscriber xSub = table.getDoubleTopic("X").subscribe(0.0)) {

            inst.addListener(xSub, EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                    event -> System.out.println("Received event " + event.valueData.value.getDouble()));

            inst.setServer("localhost");
            inst.startClient4("test subscriber");

            // Let the callback run for 10 seconds
            sleep(10000);
        }
    }
}
