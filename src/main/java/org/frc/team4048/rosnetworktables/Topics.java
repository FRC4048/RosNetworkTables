package org.frc.team4048.rosnetworktables;

import java.util.ArrayList;
import java.util.List;

public class Topics {
    List<TranslatorTopic> topics;

    public Topics() {
        topics = new ArrayList<>();
    }

    public Topics withTopic(TranslatorTopic topic) {
        topics.add(topic);
        return this;
    }
    public Topics withTopics(List<TranslatorTopic> topicList) {
        topics.addAll(topicList);
        return this;
    }

    public List<TranslatorTopic> getTopics() {
        return topics;
    }

    public void start() {
        topics.forEach(TranslatorTopic::start);
    }

    public void stop() {
        topics.forEach(TranslatorTopic::stop);
    }
}
