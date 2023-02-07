package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.internal.message.Message;
import org.ros.node.topic.Subscriber;

import java.util.stream.IntStream;

//TODO make ntPublisher Generic
public abstract class R2NtTopic<R extends Message> implements TranslatorTopic {
    // Network table publisher
    private NetworkTableEntry networkTableEntry;
    // Ros Subscriber
    private Subscriber<R> rosSubscriber;

    protected R2NtTopic(Subscriber<R> rosSubscriber, NetworkTableEntry networkTableEntry) {
        this.rosSubscriber = rosSubscriber;
        this.networkTableEntry = networkTableEntry;
    }

    @Override
    public void start() {
        // Publish value to networktables when ros subscriber receives message
        rosSubscriber.addMessageListener(this::publishToNt);
    }

    @Override
    public void stop() {
        networkTableEntry.delete();
        rosSubscriber.shutdown();
    }

    protected abstract void publishToNt(R value);

    public NetworkTableEntry getNetworkTableEntry() {
        return networkTableEntry;
    }
}
