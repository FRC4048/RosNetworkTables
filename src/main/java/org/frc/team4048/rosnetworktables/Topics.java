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

    public List<TranslatorTopic> getTopics() {
        return topics;
    }

    public void runTopics() {
        for (TranslatorTopic topic: topics) {
            topic.runTranslation();
        }
    }
}
