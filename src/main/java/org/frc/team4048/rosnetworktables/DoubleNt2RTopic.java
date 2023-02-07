package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import std_msgs.Float64;

public class DoubleNt2RTopic extends NtToRTopic<Double, Float64> {
    public DoubleNt2RTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getEntry(ntTopic), node.createPublisher(rosTopic, Float64._TYPE));
    }

    /**
     * @param value        value to populate message with
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Float64 populateMessage(Double value, Float64 emptyMessage) {
        emptyMessage.setData(value);
        return emptyMessage;
    }
}
