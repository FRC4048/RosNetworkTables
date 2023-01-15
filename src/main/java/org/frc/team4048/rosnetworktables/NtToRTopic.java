package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.Subscriber;

public abstract class NtToRTopic<T> implements TranslatorTopic {
    private Subscriber ntSubscriber;

    protected NtToRTopic(Subscriber ntSubscriber) {
        this.ntSubscriber = ntSubscriber;
    }

    public abstract T getValueOrNull();

    public Subscriber getNtSubscriber() {
        return ntSubscriber;
    }
}
