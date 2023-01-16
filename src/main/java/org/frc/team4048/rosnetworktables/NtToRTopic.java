package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Subscriber;

import java.util.EnumSet;

public abstract class NtToRTopic<T> implements TranslatorTopic {
    private T lastValue;
    private NetworkTableInstance ntInst;
    private Subscriber ntSubscriber;
    // Ros publisher

    protected NtToRTopic(NetworkTableInstance ntInst, Subscriber ntSubscriber) {
        this.ntInst = ntInst;
        this.ntSubscriber = ntSubscriber;
    }

    @Override
    public void start() {
        // Register the NT subscriber to messages
        ntInst.addListener(ntSubscriber, EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                    setLastValue((T) event.valueData.value.getValue());
                    publishToRos(event);
                });
    }

    @Override
    public void stop() {
        ntSubscriber.close();
        // release any ROS resources
    }

    /**
     * Return the cached last value received.
     * @return the last value received by this topic, NULL if none were received
     */
    public T getLastValue() {
        return lastValue;
    }

    public void setLastValue(T lastValue) {
        this.lastValue = lastValue;
    }

    public Subscriber getNtSubscriber() {
        return ntSubscriber;
    }

    protected abstract void publishToRos(NetworkTableEvent event);
}
