package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Publisher;
import org.ros.internal.message.Message;
import org.ros.message.MessageListener;
import org.ros.node.topic.Subscriber;


import java.util.EnumSet;

public abstract class R2NtTopic<R extends Message> implements TranslatorTopic {
     private NetworkTableInstance ntInst;
     private Publisher ntPublisher;
     // Ros publisher
     private Subscriber<R> rosSubscriber;

     protected R2NtTopic(NetworkTableInstance ntInst, Subscriber<R> rosSubscriber, Publisher ntPublisher) {
          this.ntInst = ntInst;
          this.rosSubscriber = rosSubscriber;
          this.ntPublisher = ntPublisher;
     }

     @Override
     public void start() {
          // Register the NT subscriber to messages
          rosSubscriber.addMessageListener(this::publishToNt);
     }

     @Override
     public void stop() {
          ntPublisher.close();
          // release any ROS resources
     }

     protected abstract void publishToNt(R value);

     public Publisher getNtPublisher() {
          return ntPublisher;
     }
}
