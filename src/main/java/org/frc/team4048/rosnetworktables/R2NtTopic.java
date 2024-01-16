package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.Publisher;
import id.jrosclient.JRosClient;
import id.jrosclient.TopicSubscriber;
import id.jrosmessages.Message;

import java.util.function.Consumer;

//TODO make ntPublisher Generic
/**
 * Base class for a ROS to NetworkTables : Receive ROS message and publish to NT.
 * @param <R> The ROS Message type
 */
public abstract class R2NtTopic<R extends Message> implements TranslatorTopic {
    private final JRosClient rosClient;
    private final TopicSubscriber<R> rosSubscriber;
    private final Publisher ntPublisher;

    protected R2NtTopic(JRosClient rosClient, Class<R> rosMessageClass, String rosTopic, Publisher ntPublisher) {
        this.rosClient = rosClient;
        this.rosSubscriber = createRosSubscriber(rosMessageClass, rosTopic);
        this.ntPublisher = ntPublisher;
    }

    @Override
    public void start() {
        rosClient.subscribe(rosSubscriber);
    }

    @Override
    public void stop() {
        ntPublisher.close();
    }

    protected abstract void publishToNt(R value);

    public Publisher getNtPublisher() {
        return ntPublisher;
    }

    private TopicSubscriber<R> createRosSubscriber(Class<R> messageClass, String topic) {
        return new TopicSubscriber<R>(messageClass, topic) {
            @Override
            public void onNext(R item) {
                publishToNt(item);
                // request next message
                getSubscription().get().request(1);
            }
        };
    }
}
