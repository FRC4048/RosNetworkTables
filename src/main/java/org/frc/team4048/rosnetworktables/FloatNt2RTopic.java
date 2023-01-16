package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.FloatSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.TimestampedFloat;
import org.frc.team4048.rosnetworktables.ros.MessageEvent;
import std_msgs.Float32;

public class FloatNt2RTopic extends NtToRTopic<Float,std_msgs.Float32> {

    public FloatNt2RTopic(NetworkTable table, String ntTopic,String rosTopic) {
        super(table.getFloatTopic(ntTopic).subscribe(0.0F));
        this.setRosPublisher(Main.getRosNode().createPublisher(rosTopic, Float32._TYPE, message -> {
            message.setData(getValueOrNull());
        }));
    }

    @Override
    public Float getValueOrNull() {
        TimestampedFloat tsValue = getAtomic();
        if (tsValue.serverTime == 0) {
            return -1f;
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
