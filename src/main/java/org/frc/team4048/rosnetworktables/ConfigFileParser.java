package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileParser {
     private final File file;
     private static final String valueSeparator = "*";

     public ConfigFileParser(String filePath) throws URISyntaxException {
          this.file = new File(ConfigFileParser.class.getClassLoader().getResource(filePath).toURI());
     }

     public List<TopicPair> readTopics() throws IOException, ClassNotFoundException {
          assert file.exists() : "Cant find config file";
          List<TopicPair> topicPairs = new ArrayList<>();
          BufferedReader bf = new BufferedReader(new FileReader(file));
          String currentLine;
          while((currentLine = bf.readLine()) != null){
               if (currentLine.isBlank())break;
               String[] segments = currentLine.split("\\^");
               Class<?> classType = Class.forName(segments[2]);
               String ntTopicName = "";
               String rosTopicName  = "";
               if (NtToRTopic.class.isAssignableFrom(classType)){
                    ntTopicName = segments[0];
                    rosTopicName = segments[1];
               } else if (R2NtTopic.class.isAssignableFrom(classType)) {
                    ntTopicName = segments[1];
                    rosTopicName = segments[0];
               }
               topicPairs.add(new TopicPair(ntTopicName,rosTopicName,classType));
          }
          return topicPairs;

     }
     public List<TranslatorTopic> createTranslators(List<TopicPair> topicPairs, RosNode node, NetworkTable table) throws InvocationTargetException, InstantiationException, IllegalAccessException {
          List<TranslatorTopic> list = new ArrayList<>();
          for (TopicPair pair: topicPairs){
               list.add((TranslatorTopic) pair.topicType.getConstructors()[0].newInstance(table,pair.ntTopic,pair.rosTopic,node));
          }
          return list;
     }
}
