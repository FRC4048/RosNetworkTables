package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import std_msgs.Float64;
import std_msgs.Float64MultiArray;

import java.util.Arrays;

public class DoubleArrayNt2RTopic extends NtToRTopic<double[], Float64MultiArray> {
    public DoubleArrayNt2RTopic(NetworkTable table, String topic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getDoubleArrayTopic(topic).subscribe(new double[]{}),node.createPublisher(rosTopic,Float64MultiArray._TYPE));
    }

    /**
     * @param value value to populate message with
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Float64MultiArray populateMessage(double[] value,Float64MultiArray emptyMessage) {
        emptyMessage.setData(value);
        return emptyMessage;
    }
}
