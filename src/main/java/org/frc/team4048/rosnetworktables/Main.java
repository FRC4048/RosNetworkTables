package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc.team4048.rosnetworktables.ros.RosNode;

import java.net.URISyntaxException;

import static java.lang.Thread.sleep;

public class Main {

    private static RosNode rosNode;
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        rosNode = new RosNode("http://10.40.48.95:11311","10.40.48.153");
        Topics topics = initializeTopics();
//        while (true) {
//            topics.runTopics();
//            sleep(1000);
//        }
    }

    private static Topics initializeTopics() {
        Topics topics = new Topics();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("SmartDashboard");
        topics.withTopic(new DoubleNt2RTopic(table, "BackLeft AIO","testRobotImu"));

        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient4("example client");

        return topics;
    }

    public static RosNode getRosNode() {
        return rosNode;
    }
}
