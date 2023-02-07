package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.frc.team4048.rosnetworktables.Constants.NT_TABLE_NAME;

public class NtRosProxy {
     private static NtRosProxy instance;

     public synchronized static NtRosProxy get() {
          if(instance == null) instance = new NtRosProxy();
          return instance;
     }

     private RosNode rosNode;
     private final String rosMasterURI;
     private final String rosHostname;
     private final String networkTablesIP;
     private Topics topics;
     private NetworkTableInstance ntInstance;
     private NetworkTable ntTable;
     private boolean started = false;

     private NtRosProxy() {
          String rosMasterURI = System.getenv(Constants.ROS_MASTER_URI);
          assert rosMasterURI != null : "Environment Variable not set " + Constants.ROS_MASTER_URI;

          String networkTablesIP = System.getenv(Constants.NT_IP);
          assert networkTablesIP != null : "Environment Variable not set " + Constants.NT_IP;

          String tempHostName = "0.0.0.0";
          try {
               tempHostName = InetAddress.getLocalHost().getHostAddress();
          } catch (UnknownHostException ignore){
          }

          this.rosMasterURI = rosMasterURI;
          this.networkTablesIP = networkTablesIP;
          this.rosHostname = tempHostName;
     }

     //TODO Add timeout
     public void start() throws InterruptedException, IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
          if(started) return;
          started = true;
          initRosNode();
//      Prints waiting every second while we wait for rosNode to init
//          TODO add time out
          long st = System.currentTimeMillis();
          while (!rosNode.isInitialized()){
               if(System.currentTimeMillis()-st >=1000){
                    System.out.println("WAITING");
                    st=System.currentTimeMillis();
               }
               sleep(100);
          }
          initNetworkTables();
          initializeTopics();
          topics.start();
     }

     /**
      * TODO call this at some point
      */
     public void stop(){
          if (!started) return;
          topics.stop();
          rosNode.stop();
          started = false;
     }

     /**
      * creates topics from configuration file 'config.carrot'
      * @throws IOException
      * @throws ClassNotFoundException
      * @throws InvocationTargetException
      * @throws InstantiationException
      * @throws IllegalAccessException
      */
     private void initializeTopics() throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
          topics = new Topics();
          ConfigFileParser parser = new ConfigFileParser("config.carrot");
          parser.readTopics();
          parser.createTranslators(rosNode, ntTable);
          topics.withTopics(parser.getTranslators());

     }

     /**
      * create ros node that handles ros publishers and subscribers
      */
     private void initRosNode(){
          NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
          rosNode = new RosNode();
          NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(rosHostname);
          nodeConfiguration.getTimeProvider().getCurrentTime();
          nodeConfiguration.setNodeName(GraphName.empty());
          nodeConfiguration.setMasterUri(URI.create(rosMasterURI));
          nodeMainExecutor.execute(rosNode,nodeConfiguration);
     }

     /**
      * starts a network table Client that publishes and subscribes to data
      */
     private void initNetworkTables(){
          ntInstance =  NetworkTableInstance.getDefault();
          ntInstance.setServer(networkTablesIP);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
//          inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
          ntInstance.startClient();
          ntTable = ntInstance.getTable(NT_TABLE_NAME);
     }
}
