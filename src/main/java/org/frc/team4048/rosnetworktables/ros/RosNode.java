package org.frc.team4048.rosnetworktables.ros;

import org.ros.internal.message.Message;
import org.ros.message.MessageListener;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.net.URI;
import java.net.URISyntaxException;

public class RosNode {
     private final String ROS_MASTER_URI;
     private final URI rosMasterUri;
     private final String ROS_IP;
     private final NodeMainExecutor nodeMainExecutor;

     public RosNode(String ROS_MASTER_URI,String ROS_IP) throws URISyntaxException {
          this.ROS_MASTER_URI = ROS_MASTER_URI;
          this.ROS_IP = ROS_IP;
          this.rosMasterUri = new URI(ROS_MASTER_URI);
          this.nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
     }

     public <T extends Message> RosSubscriber<T> createSubscriber(String topicName, String topicType, MessageListener<T> subscribeEvent){
          RosSubscriber<T> topicSubscriberNodeMain = new RosSubscriber<>(topicName, topicType, subscribeEvent);
          NodeConfiguration topicSubscriberNodeConfiguration = getNodeConfiguration(topicName);
          nodeMainExecutor.execute(topicSubscriberNodeMain, topicSubscriberNodeConfiguration);
          return topicSubscriberNodeMain;
     }
     public <T extends Message> RosPublisher<T> createPublisher(String topicName, String topicType, MessageEvent<T> publishEvent){
          RosPublisher<T> topicPublisherNodeMain = new RosPublisher<>(topicName, topicType,publishEvent);
          NodeConfiguration topicPublisherNodeConfiguration = getNodeConfiguration(topicName);
          nodeMainExecutor.execute(topicPublisherNodeMain, topicPublisherNodeConfiguration);
          return topicPublisherNodeMain;
     }

     private NodeConfiguration getNodeConfiguration(final String nodeName) {
          //Create a node configuration
          NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(ROS_IP);
          nodeConfiguration.setNodeName(nodeName);
          nodeConfiguration.setMasterUri(rosMasterUri);
          return nodeConfiguration;
     }
}
