package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import std_msgs.Float64;

public class DoubleR2NtTopic extends R2NtTopic<Float64> {

    public DoubleR2NtTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), node.createSubscriber(rosTopic, Float64._TYPE), table.getInstance().getEntry(ntTopic));
    }

    @Override
    protected void publishToNt(Float64 value) {
        getNetworkTableEntry().setDouble(value.getData());
    }

//     public DoublePublisher narrow(){
//          return (DoublePublisher) this.getNetworkTableEntry();
//     }

}
