package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import std_msgs.Float32;


public class FloatNt2RTopic extends NtToRTopic<Float, Float32> {
    public FloatNt2RTopic(NetworkTable table, String topic,String rosTopic,RosNode node) {
        super(table.getInstance(), table.getDoubleTopic(topic).subscribe(0.0D),node.createPublisher(rosTopic, Float32._TYPE));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        System.out.println("Received double " + event.valueData.value.getDouble());
        if (getRosPublisher() == null) System.out.println("Cant Publish " + event.valueData.value.getDouble() + " Because publisher is null");
        getRosPublisher().publish(populateMessage(event.valueData.value.getFloat(),getRosPublisher().newMessage()));
    }

    @Override
    protected Float32 populateMessage(Float value,Float32 emptyMessage) {
        emptyMessage.setData(value);
        return emptyMessage;
    }
}
