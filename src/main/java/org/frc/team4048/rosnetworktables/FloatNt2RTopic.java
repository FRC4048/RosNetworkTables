package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;

public class FloatNt2RTopic extends NtToRTopic<Float> {
    public FloatNt2RTopic(NetworkTable table, String topic) {
        super(table.getInstance(), table.getFloatTopic(topic).subscribe(0.0F));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        System.out.println("Received float " + event.valueData.value.getFloat());
    }
}
