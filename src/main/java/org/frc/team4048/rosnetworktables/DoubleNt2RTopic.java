package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.*;

public class DoubleNt2RTopic extends NtToRTopic<Double> {

    public DoubleNt2RTopic(NetworkTable table, String topic) {
        super(table.getDoubleTopic(topic).subscribe(0.0D));
    }

    @Override
    public Double getValueOrNull() {
        TimestampedDouble tsValue = getAtomic();
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

    private TimestampedDouble getAtomic() {
        return narrow().getAtomic();
    }

    private DoubleSubscriber narrow() {
        return (DoubleSubscriber) getNtSubscriber();
    }
}
