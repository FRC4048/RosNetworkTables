package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileParser {
     private static final String commentChar = "//";
     private static final String separatorChar = "\\^";

     public static List<TopicPair> readTopics(String filePath) throws IOException, ClassNotFoundException, URISyntaxException {
          File file = new File(ConfigFileParser.class.getClassLoader().getResource(filePath).toURI());
          assert file.exists() : "Cant find config file";
          List<TopicPair> topicPairs = new ArrayList<>();
          BufferedReader bf = new BufferedReader(new FileReader(file));
          String currentLine;
          while((currentLine = bf.readLine()) != null){
               if (currentLine.isBlank())break;
               if(currentLine.startsWith(commentChar))continue;
               String[] segments = currentLine.split(separatorChar);
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
               assert (rosTopicName.isBlank() || ntTopicName.isBlank()) : "Invalid RosTopic or NetworkTopic name";
               topicPairs.add(new TopicPair(ntTopicName,rosTopicName,classType));
          }
          return topicPairs;

     }
     public static List<TranslatorTopic> createTranslators(List<TopicPair> topicPairs, RosNode node, NetworkTable table) throws InvocationTargetException, InstantiationException, IllegalAccessException {
          List<TranslatorTopic> list = new ArrayList<>();
          for (TopicPair pair: topicPairs){
               list.add((TranslatorTopic) pair.topicType.getConstructors()[0].newInstance(table,pair.ntTopic,pair.rosTopic,node));
          }
          return list;
     }
}
