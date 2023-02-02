package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Publisher;
import org.ros.node.topic.Subscriber;
import std_msgs.Float64;

public class DoubleR2NtTopic extends R2NtTopic<Float64>{

     protected DoubleR2NtTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
          super(table.getInstance(), node.createSubscriber(rosTopic,Float64._TYPE),table.getDoubleTopic(ntTopic).publish());
     }

     @Override
     protected void publishToNt(Float64 value) {
          narrow().set(value.getData());
     }

     public DoublePublisher narrow(){
          return (DoublePublisher) this.getNtPublisher();
     }

}
