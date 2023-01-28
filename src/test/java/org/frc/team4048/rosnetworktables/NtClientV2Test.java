package org.frc.team4048.rosnetworktables;


import edu.wpi.first.networktables.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;

import static java.lang.Thread.sleep;

public class NtClientV2Test {

    @Test
    public void sendToNetworkTablesTopic() throws Exception {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("TestTopic");
        try (DoubleArrayPublisher pub = table.getDoubleArrayTopic("MyOdom").publish()) {
            inst.setServer("10.40.48.1");
            inst.setServerTeam(4048);
            inst.startClient4("test publisher");
            while (true) {
                sleep(1000);
                double[] value = new double[]{Math.random() * 10, Math.random() * 10, Math.random() * 10};
                pub.set(value);
                System.out.println("Sent x: " + Arrays.toString(value));
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
