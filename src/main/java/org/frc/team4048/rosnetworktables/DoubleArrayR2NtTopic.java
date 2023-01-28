package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import std_msgs.Float64;
import std_msgs.Float64MultiArray;

public class DoubleArrayR2NtTopic extends R2NtTopic<Float64MultiArray>{

     protected DoubleArrayR2NtTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
          super(table.getInstance(), node.createSubscriber(rosTopic,Float64MultiArray._TYPE),table.getDoubleTopic(ntTopic).publish());
     }

     @Override
     protected void publishToNt(Float64MultiArray value) {
          narrow().set(value.getData());
     }

     public DoubleArrayPublisher narrow(){
          return (DoubleArrayPublisher) this.getNtPublisher();
     }

}
