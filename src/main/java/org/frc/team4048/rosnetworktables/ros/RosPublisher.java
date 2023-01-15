package org.frc.team4048.rosnetworktables.ros;

import org.ros.concurrent.CancellableLoop;
import org.ros.internal.message.Message;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class RosPublisher<T extends Message> extends AbstractNodeMain {
     private final String rosTopicName;
     private final String rosTopicType;
     private final MessageEvent<T> messageEvent;

     protected RosPublisher(final String rosTopicName, String rosTopicType, MessageEvent<T> messageEvent) {
          this.rosTopicName = rosTopicName;
          this.rosTopicType = rosTopicType;
          this.messageEvent = messageEvent;
     }

     @Override
     public GraphName getDefaultNodeName() {
          return GraphName.of("/");
     }

     @Override
     public void onStart(ConnectedNode connectedNode) {
          System.out.println("Starting");
          Publisher<T> publisher = connectedNode.newPublisher(this.rosTopicName, rosTopicType);
          //The CancellableLoop will run again and again until the node is stopped.
          connectedNode.executeCancellableLoop(new CancellableLoop() {

               @Override
               protected void loop() throws InterruptedException {
                    //Create a new blank message
                    T message = publisher.newMessage();
                    //Add some information to the message
                    messageEvent.create(message);

                    //publish the message to the topic
                    publisher.publish(message);
//                    System.out.println("publishing");

                    //wait for SLEEP_DURATION_MILLIS to throttle the rate of the  published messages
                    Thread.sleep(1000);
               }
          });
     }
}

