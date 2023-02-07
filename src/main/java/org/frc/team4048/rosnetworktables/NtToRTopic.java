package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.jetbrains.annotations.NotNull;
import org.ros.internal.message.Message;
import org.ros.node.topic.Publisher;

public abstract class NtToRTopic<T, R extends Message> implements TranslatorTopic {
    private NetworkTableInstance ntInst;
    private NetworkTableEntry networkTableEntry;
    // Ros publisher
    private Publisher<R> rosPublisher;

    protected NtToRTopic(NetworkTableInstance ntInst, NetworkTableEntry networkTableEntry, Publisher<R> rosPublisher) {
        this.ntInst = ntInst;
        this.networkTableEntry = networkTableEntry;
        this.rosPublisher = rosPublisher;
    }

    @Override
    public void start() {
        // Register the NT subscriber to messages
        ntInst.addEntryListener(networkTableEntry,
                entryNotification -> publishToRos((T) entryNotification.value.getValue()), EntryListenerFlags.kLocal | EntryListenerFlags.kUpdate);
    }

    @Override
    public void stop() {
        // networkTableEntry.close();
        // release any ROS resources
    }


    private void publishToRos(T value) {
        getRosPublisher().publish(populateMessage(value, getRosPublisher().newMessage()));
    }

    protected abstract R populateMessage(T value, R emptyMessage);

    @NotNull
    public Publisher<R> getRosPublisher() {
        return rosPublisher;
    }
}
