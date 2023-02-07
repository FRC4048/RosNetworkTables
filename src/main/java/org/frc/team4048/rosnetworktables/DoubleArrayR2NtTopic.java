package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import std_msgs.Float64MultiArray;

public class DoubleArrayR2NtTopic extends R2NtTopic<Float64MultiArray> {

    protected DoubleArrayR2NtTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), node.createSubscriber(rosTopic, Float64MultiArray._TYPE), table.getInstance().getEntry(ntTopic));
    }

    @Override
    protected void publishToNt(Float64MultiArray value) {
        getNetworkTableEntry().setDoubleArray(value.getData());
    }

}
