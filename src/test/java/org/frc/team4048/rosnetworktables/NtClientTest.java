package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.junit.jupiter.api.Test;

public class NtClientTest {

    @Test
    public void registerToSmartDashboardTopic() throws Exception {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("SmartDashboard");
        DoubleSubscriber xSub = table.getDoubleTopic("BackLeft AIO").subscribe(0.0);
        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient4("example client");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("interrupted");
                return;
            }
            double x = xSub.get();
            System.out.println("X: " + x);
        }

    }
}
