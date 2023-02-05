package org.frc.team4048.rosnetworktables;

public class TopicPair {
     public final String ntTopic;
     public final String rosTopic;
     public final Class<? extends TranslatorTopic> topicType;

     public TopicPair(String ntTopic, String rosTopic, Class<? extends TranslatorTopic> topicType) {
          this.ntTopic = ntTopic;
          this.rosTopic = rosTopic;
          this.topicType = topicType;

     }
}
