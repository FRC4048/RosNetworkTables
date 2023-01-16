package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Topics topics = initializeTopics();
        topics.start();
        sleep(10000);
        topics.stop();
    }

    private static Topics initializeTopics() {
        Topics topics = new Topics();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("TestTopic");
        topics.withTopic(new DoubleNt2RTopic(table, "X"));

        inst.setServer("localhost");  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
//        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient4("example client");

        return topics;
    }
}
