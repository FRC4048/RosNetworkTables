package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Topics topics = initializeTopics();
        while (true) {
            topics.runTopics();
            sleep(1000);
        }
    }

    private static Topics initializeTopics() {
        Topics topics = new Topics();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("SmartDashboard");
        topics.withTopic(new DoubleNt2RTopic(table, "BackLeft AIO"));

        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient4("example client");

        return topics;
    }
}
