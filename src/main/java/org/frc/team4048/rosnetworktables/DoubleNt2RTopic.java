package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import std_msgs.Float64;

public class DoubleNt2RTopic extends NtToRTopic<Double,Float64> {
    public DoubleNt2RTopic(NetworkTable table, String topic,String rosTopic,RosNode node) {
        super(table.getInstance(), table.getDoubleTopic(topic).subscribe(0.0D),node.createPublisher(rosTopic,Float64._TYPE));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        double value = event.valueData.value.getDouble();
        System.out.println("Received double " + value);
        if (getRosPublisher() == null) System.out.println("Cant Publish " + value + " Because publisher is null");
        getRosPublisher().publish(populateMessage(value,getRosPublisher().newMessage()));
    }

    /**
     * @param value value to populate message with
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Float64 populateMessage(Double value,Float64 emptyMessage) {
        emptyMessage.setData(value);
        return emptyMessage;
    }
}
