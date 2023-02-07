package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import std_msgs.Float32;


public class FloatNt2RTopic extends NtToRTopic<Float, Float32> {
    public FloatNt2RTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getEntry(ntTopic), node.createPublisher(rosTopic, Float32._TYPE));
    }

    @Override
    protected Float32 populateMessage(Float value, Float32 emptyMessage) {
        emptyMessage.setData(value);
        return emptyMessage;
    }
}
