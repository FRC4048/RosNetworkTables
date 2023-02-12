package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Subscriber;
import org.jetbrains.annotations.NotNull;
import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

import java.util.Arrays;
import java.util.EnumSet;

public abstract class NtToRTopic<T,R extends Message> implements TranslatorTopic {
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
// TODO this is bad practice needs to be look at
                event -> {
            publishToRos((T)event.valueData.value.getValue());
                });
    }

    @Override
    public void stop() {
        ntSubscriber.close();
        // release any ROS resources
    }


    private void publishToRos(T value){
        getRosPublisher().publish(populateMessage(value,getRosPublisher().newMessage()));
    }

    protected abstract R populateMessage(T value, R emptyMessage);

    @NotNull
    public Publisher<R> getRosPublisher() {
        return rosPublisher;
    }
}
