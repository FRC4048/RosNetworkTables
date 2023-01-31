package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class NtRosProxy {
     private static NtRosProxy instance;

     public static NtRosProxy get() {
          if(instance == null) instance = new NtRosProxy();
          return instance;
     }

     private RosNode rosNode;
     private final String rosMasterURI;
     private final String rosHostname;
     private final String networkTablesIP;
     private Topics topics;
     private boolean started = false;

     private NtRosProxy() {
          String rosMasterURI = System.getenv(Constants.ROS_MASTER_URI);
          assert rosMasterURI != null : "Environment Variable not set " + Constants.ROS_MASTER_URI;

          String networkTablesIP = System.getenv(Constants.NT_IP);
          assert networkTablesIP != null : "Environment Variable not set " + Constants.NT_IP;

          String tempHostName = "0.0.0.0";
          try { tempHostName = InetAddress.getLocalHost().getHostAddress();
          }catch (UnknownHostException ignore){}

          this.rosMasterURI = rosMasterURI;
          this.networkTablesIP = networkTablesIP;
          this.rosHostname = tempHostName;
     }

     public void start() {
          if(started) return;
          started = true;
          initRosNode();
//      Prints waiting every second while we wait for rosNode to init
          long st = System.currentTimeMillis();
          while (!rosNode.isInitialized()){
               if(System.currentTimeMillis()-st >=1000){
                    System.out.println("WAITING");
                    st=System.currentTimeMillis();
               }
          }
          this.topics = initializeTopics();
          topics.start();
     }
     public void stop(){
          topics.stop();
          started = false;
     }

     /**
      * NOTE topics will be at some point read from file instead of hard coded
      * @return List of topics application will handle
      */
     private Topics initializeTopics() {
          Topics topics = new Topics();
          NetworkTableInstance inst = NetworkTableInstance.getDefault();
          NetworkTable table = inst.getTable("Shuffleboard/Test");
          topics.withTopic(new DoubleArrayNt2CustomPointTopic(table, "field","imu",rosNode));
          inst.setServer(networkTablesIP);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
          inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
          inst.startClient4("example client");
          return topics;
     }
     private void initRosNode(){
          NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
          rosNode = new RosNode();
          NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(rosHostname);
          nodeConfiguration.getTimeProvider().getCurrentTime();
          nodeConfiguration.setNodeName(GraphName.empty());
          nodeConfiguration.setMasterUri(URI.create(rosMasterURI));
          nodeMainExecutor.execute(rosNode,nodeConfiguration);
     }
}
