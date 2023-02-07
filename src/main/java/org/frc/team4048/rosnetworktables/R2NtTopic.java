package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.ros.internal.message.Message;
import org.ros.node.topic.Subscriber;

//TODO make ntPublisher Generic
public abstract class R2NtTopic<R extends Message> implements TranslatorTopic {
    private NetworkTableInstance ntInst;
    // Network table publisher
    private NetworkTableEntry networkTableEntry;
    // Ros Subscriber
    private Subscriber<R> rosSubscriber;

    protected R2NtTopic(NetworkTableInstance ntInst, Subscriber<R> rosSubscriber, NetworkTableEntry networkTableEntry) {
        this.ntInst = ntInst;
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
//          networkTableEntry.close();
        rosSubscriber.shutdown();
    }

    protected abstract void publishToNt(R value);

    public NetworkTableEntry getNetworkTableEntry() {
        return networkTableEntry;
    }
}
