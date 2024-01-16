package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Subscriber;
import id.jrosclient.JRosClient;
import id.jrosclient.TopicSubmissionPublisher;
import id.jrosmessages.Message;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * Base class for a NetworkTables to ROS topic: Receive NT message and publish to ROS.
 * @param <T> The NetworkTables type
 * @param <R> The ROS Message type
 */
public abstract class NtToRTopic<T, R extends Message> implements TranslatorTopic {
    private final NetworkTableInstance ntInst;
    private final Subscriber ntSubscriber;
    private final TopicSubmissionPublisher<R> rosPublisher;

    protected NtToRTopic(NetworkTableInstance ntInst, Subscriber ntSubscriber,
                         TopicSubmissionPublisher<R> rosPublisher) {
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
                    publishToRos((T) event.valueData.value.getValue());
                });
    }

    @Override
    public void stop() {
        ntSubscriber.close();
        // release any ROS resources
        rosPublisher.close();
    }

    private void publishToRos(T value) {
        getRosPublisher().submit(populateMessage(value));
    }

    protected abstract R populateMessage(T value);

    @NotNull
    public TopicSubmissionPublisher<R> getRosPublisher() {
        return rosPublisher;
    }

    protected static <R extends Message> TopicSubmissionPublisher<R> createRosPublisher(JRosClient client, Class<R> rosMessageClass, String topicName) {
        TopicSubmissionPublisher<R> publisher = new TopicSubmissionPublisher<>(rosMessageClass, topicName);
        // register a new publisher for a new topic with ROS
        client.publish(publisher);
        return publisher;
    }
}
