package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.namespace.GraphName;
import org.ros.node.*;
import org.ros.node.topic.Publisher;
import std_msgs.Float64;

import java.net.URI;

import static java.lang.Thread.sleep;

public class Main {
    static RosNode rosNode;

    public static void main(String[] args) throws InterruptedException {
        initRosNode("10.12.0.235",URI.create("http://10.11.7.137:11311"));
//      Prints waiting every second while we wait for rosNode to init
        long st = System.currentTimeMillis();
        while (!rosNode.isInitialized()){
            if(System.currentTimeMillis()-st >=1000){
                System.out.println("WAITING");
                st=System.currentTimeMillis();
            }
        }
        Topics topics = initializeTopics();
        topics.start();
        sleep(60000);
        topics.stop();
    }

    private static Topics initializeTopics() {
        Topics topics = new Topics();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("Shuffleboard/Swerve");
        topics.withTopic(new DoubleNt2RTopic(table, "imu","imu",rosNode));
        topics.withTopic(new DoubleR2NtTopic(table, "Y","testPub",rosNode));
        inst.setServer("localhost");  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
//        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient3("example client");


        return topics;
    }
    private static void initRosNode(String hostIp, URI rosMasterUri){
        NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
        rosNode = new RosNode();
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(hostIp);
        nodeConfiguration.setNodeName(GraphName.empty());
        nodeConfiguration.setMasterUri(rosMasterUri);
        nodeMainExecutor.execute(rosNode,nodeConfiguration);
    }

}
