package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.*;
import std_msgs.Float32;
import std_msgs.Float64;

public class DoubleNt2RTopic extends NtToRTopic<Double,std_msgs.Float64> {

    public DoubleNt2RTopic(NetworkTable table, String nt_topic,String topic) {
        super(table.getDoubleTopic(nt_topic).subscribe(0.0D));
        this.setRosPublisher(Main.getRosNode().createPublisher(topic, Float64._TYPE, message -> message.setData(getValueOrNull())));
    }

    @Override
    public Double getValueOrNull() {
        TimestampedDouble tsValue = getAtomic();
        if (tsValue.serverTime == 0) {
            return -1d;
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
