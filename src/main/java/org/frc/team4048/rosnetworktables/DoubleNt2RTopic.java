package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.*;

public class DoubleNt2RTopic extends NtToRTopic<Double> {
    public DoubleNt2RTopic(NetworkTable table, String topic) {
        super(table.getInstance(), table.getDoubleTopic(topic).subscribe(0.0D));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        System.out.println("Received double " + event.valueData.value.getDouble());
    }
}
