package org.frc.team4048.rosnetworktables;

public class TopicPair {
     public final String ntTopic;
     public final String rosTopic;
     public final Class<?> topicType;


     public TopicPair(String ntTopic, String rosTopic, Class<?> topicType) {
          this.ntTopic = ntTopic;
          this.rosTopic = rosTopic;
          this.topicType = topicType;
//          System.out.println(this);

     }

     @Override
     public String toString() {
          return "TopicPair{" +
                  "ntTopic='" + ntTopic + '\'' +
                  ", rosTopic='" + rosTopic + '\'' +
                  ", topicType=" + topicType +
                  '}';
     }
}
