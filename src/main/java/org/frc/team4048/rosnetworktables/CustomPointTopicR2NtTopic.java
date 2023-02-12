package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.*;
import geometry_msgs.Point;
import geometry_msgs.PointStamped;
import org.ros.node.topic.Subscriber;
import std_msgs.Float64;

public class CustomPointTopicR2NtTopic extends R2NtTopic<PointStamped> {

    public CustomPointTopicR2NtTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), node.createSubscriber(rosTopic, PointStamped._TYPE),table.getDoubleArrayTopic(ntTopic).publish());
    }

    @Override
    protected void publishToNt(PointStamped value) {
        narrow().set(new double[]{value.getPoint().getX(),value.getPoint().getY(),value.getPoint().getZ()});
    }

    public DoubleArrayPublisher narrow(){
        return (DoubleArrayPublisher) this.getNtPublisher();
    }
}
