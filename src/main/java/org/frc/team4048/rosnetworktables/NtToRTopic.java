package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Subscriber;
import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

import java.util.EnumSet;

public abstract class NtToRTopic<T,R extends Message> implements TranslatorTopic {
    private T lastValue;
    private NetworkTableInstance ntInst;
    private Subscriber ntSubscriber;
    // Ros publisher
    private Publisher<R> rosPublisher;

    protected NtToRTopic(NetworkTableInstance ntInst, Subscriber ntSubscriber,Publisher<R> rosPublisher) {
        this.ntInst = ntInst;
        this.ntSubscriber = ntSubscriber;
        this.rosPublisher = rosPublisher;
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

    protected abstract R populateMessage(T value, R emptyMessage);

    public Publisher<R> getRosPublisher() {
        return rosPublisher;
    }
}
