package org.frc.team4048.rosnetworktables;

import java.util.ArrayList;
import java.util.List;

public class Topics {
    List<TranslatorTopic> topics;

    public Topics() {
        topics = new ArrayList<>();
    }

    /**
     * addes the topic provided to the local {@link #topics} list
     * @param topic topic you want to add
     * @return
     */
    public Topics withTopic(TranslatorTopic topic) {
        topics.add(topic);
        return this;
    }

    /**
     * adds every topic in this list to the local {@link #topics} list
     * @param topicList list of topics you want to add
     * @return current object (used for chaining methods)
     */
    public Topics withTopics(List<TranslatorTopic> topicList) {
        topics.addAll(topicList);
        return this;
    }

    public List<TranslatorTopic> getTopics() {
        return topics;
    }

    /**
     * starts every topic in the {@link #topics} list
     * @see #withTopic(TranslatorTopic)
     * @see #withTopics(List)
     */
    public void start() {
        topics.forEach(TranslatorTopic::start);
    }

    /**
     * stops every topic in the {@link #topics} list
     * @see #withTopic(TranslatorTopic)
     * @see #withTopics(List)
     */
    public void stop() {
        topics.forEach(TranslatorTopic::stop);
    }
}
