package org.frc.team4048.rosnetworktables;

import org.ros.internal.message.Message;
import org.ros.namespace.GraphName;
import org.ros.node.*;
import org.ros.node.topic.Publisher;

import javax.annotation.Nullable;

public class RosNode extends AbstractNodeMain {
     private ConnectedNode node;
     private boolean initialized;

     public RosNode() {
          initialized = false;
     }

     @Nullable
     public <T extends Message> Publisher<T> createPublisher(String topicName, String topicType){
          if (!initialized) return null;
          return node.newPublisher(topicName, topicType);
     }


     @Override
     public GraphName getDefaultNodeName() {
          return GraphName.empty();
     }

     @Override
     public void onStart(ConnectedNode connectedNode) {
          this.node = connectedNode;
          this.initialized = true;
     }

     /**
      * Not sure what the logic should be for this yet
      * @param node node to be shutdown
      */
     @Override
     public void onShutdown(Node node) {
//          node.removeListeners();
//          node.shutdown();
     }

     public boolean isInitialized() {
          return this.initialized;
     }
}