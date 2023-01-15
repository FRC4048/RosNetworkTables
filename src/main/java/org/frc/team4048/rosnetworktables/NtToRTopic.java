package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.Subscriber;
import org.frc.team4048.rosnetworktables.ros.RosPublisher;
import org.ros.internal.message.Message;

public abstract class NtToRTopic<T,R extends Message> implements TranslatorTopic {
    private Subscriber ntSubscriber;
    private RosPublisher<R> rosPublisher;

    protected NtToRTopic(Subscriber ntSubscriber) {
        this.ntSubscriber = ntSubscriber;
    }
    protected void setRosPublisher(RosPublisher<R> rosPublisher){
        this.rosPublisher = rosPublisher;
    }

    public abstract T getValueOrNull();

    public Subscriber getNtSubscriber() {
        return ntSubscriber;
    }
    public RosPublisher<R> getRosPublisher(){
        return this.rosPublisher;
    }
}
