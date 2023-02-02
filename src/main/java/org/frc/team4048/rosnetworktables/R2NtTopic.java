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
     // Network table publisher
     private Publisher ntPublisher;
     // Ros Subscriber
     private Subscriber<R> rosSubscriber;

     protected R2NtTopic(NetworkTableInstance ntInst, Subscriber<R> rosSubscriber, Publisher ntPublisher) {
          this.ntInst = ntInst;
          this.rosSubscriber = rosSubscriber;
          this.ntPublisher = ntPublisher;
     }

     @Override
     public void start() {
          // Publish value to networktables when ros subscriber receives message
          rosSubscriber.addMessageListener(this::publishToNt);
     }

     @Override
     public void stop() {
          ntPublisher.close();
          rosSubscriber.shutdown();
     }

     protected abstract void publishToNt(R value);

     public Publisher getNtPublisher() {
          return ntPublisher;
     }
}
