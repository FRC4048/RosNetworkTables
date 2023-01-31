package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;

public class Main {
    private static RosNode rosNode;

    public static void main(String[] args) throws InterruptedException {
        initRosNode("10.40.48.223",URI.create("http://10.40.48.95:11311"));
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
//        sleep(60000);
//        topics.stop();
    }

    private static Topics initializeTopics() {
        Topics topics = new Topics();
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("Shuffleboard/Test");
        topics.withTopic(new DoubleArrayNt2CustomPointTopic(table, "field","imu",rosNode));
        inst.setServer("10.40.48.1");  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        inst.startClient4("example client");


        return topics;
    }
    private static void initRosNode(String hostIp, URI rosMasterUri){
        NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
        rosNode = new RosNode();
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(hostIp);
        nodeConfiguration.getTimeProvider().getCurrentTime();
        nodeConfiguration.setNodeName(GraphName.empty());
        nodeConfiguration.setMasterUri(rosMasterUri);
        nodeMainExecutor.execute(rosNode,nodeConfiguration);
    }

}
