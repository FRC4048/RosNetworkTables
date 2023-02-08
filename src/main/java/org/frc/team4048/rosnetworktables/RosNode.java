package org.frc.team4048.rosnetworktables;

import org.ros.internal.message.Message;
import org.ros.namespace.GraphName;
import org.ros.node.*;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import javax.annotation.Nullable;

public class RosNode extends AbstractNodeMain {
     private ConnectedNode node;
     private boolean initialized;

     public RosNode() {
          initialized = false;
     }

     /**
      * Creates a {@link org.ros.node.topic.Publisher RosPublisher} on the topic with the given name and type
      * @param topicName the name of the topic
      * @param topicType the string representation of ros message type
      * @param <T> Type of message the publisher is writing to
      * @return {@link org.ros.node.topic.Subscriber RosSubscriber}
      */
     @Nullable
     public <T extends Message> Publisher<T> createPublisher(String topicName, String topicType){
          if (!initialized) return null;
          return node.newPublisher(topicName, topicType);
     }

     /**
      * Creates a {@link org.ros.node.topic.Subscriber RosSubscriber} on the topic with the given name and type
      * @param topicName the name of the topic
      * @param topicType the string representation of ros message type
      * @param <T> Type of message the subscriber is listening to
      * @return {@link org.ros.node.topic.Subscriber RosSubscriber}
      */
     @Nullable
     public <T extends Message> Subscriber<T> createSubscriber(String topicName, String topicType){
          if (!initialized) return null;
          return node.newSubscriber(topicName, topicType);
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
      * TODO Not sure what the logic should be for this yet
      * @param node node to be shutdown
      */
     @Override
     public void onShutdown(Node node) {
          node.removeListeners();
          node.shutdown();
     }

     public boolean isInitialized() {
          return this.initialized;
     }


     public void stop() {
          if (!initialized)return;
          node.shutdown();
          initialized = false;
     }
}
