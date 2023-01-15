package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.FloatSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.TimestampedFloat;

public class FloatNt2RTopic extends NtToRTopic<Float> {

    public FloatNt2RTopic(NetworkTable table, String topic) {
        super(table.getFloatTopic(topic).subscribe(0.0F));
    }

    @Override
    public Float getValueOrNull() {
        TimestampedFloat tsValue = getAtomic();
        if (tsValue.serverTime == 0) {
            return null;
        } else {
            return tsValue.value;
        }
    }

    @Override
    public void runTranslation() {
        System.out.println("Float value is " + getValueOrNull());
    }

    private TimestampedFloat getAtomic() {
        return narrow().getAtomic();
    }

    private FloatSubscriber narrow() {
        return (FloatSubscriber) getNtSubscriber();
    }
}
