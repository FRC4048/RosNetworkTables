package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileParser {
     private static final String commentChar = "//";
     private static final String separatorChar = " ";
     private final File file;
     private List<TopicPair> topicPairList;
     private List<TranslatorTopic> translators;
     public ConfigFileParser(String filePath) throws URISyntaxException {
          this.file = new File(ConfigFileParser.class.getClassLoader().getResource(filePath).toURI());
     }

     public void readTopics() throws IOException, ClassNotFoundException {
          assert file.exists() : "Cant find config file";
          List<TopicPair> topicPairs = new ArrayList<>();
          BufferedReader bf = new BufferedReader(new FileReader(file));
          String currentLine;
          int i = -1;
          while((currentLine = bf.readLine()) != null){
               i++;
               if (currentLine.isBlank())continue;
               if(currentLine.startsWith(commentChar))continue;
               String[] segments = currentLine.split(separatorChar);
               assert segments.length == 3 : "Incorrect Number of Arguments on line " + i;

               Class<? extends TranslatorTopic> classType = Class.forName(segments[2]).asSubclass(TranslatorTopic.class);
               String ntTopicName = segments[0];
               String rosTopicName = segments[1];
               assert (rosTopicName.isBlank() || ntTopicName.isBlank()) : "Invalid RosTopic or NetworkTopic name on line " + i;
               topicPairs.add(new TopicPair(ntTopicName,rosTopicName,classType));
          }
          this.topicPairList = topicPairs;

     }
     public void createTranslators(RosNode node, NetworkTable table) throws InvocationTargetException, InstantiationException, IllegalAccessException {
          List<TranslatorTopic> list = new ArrayList<>();
          for (TopicPair pair: topicPairList){
               list.add((TranslatorTopic) pair.topicType.getConstructors()[0].newInstance(table,pair.ntTopic,pair.rosTopic,node));
          }
          this.translators = list;
     }

     public List<TranslatorTopic> getTranslators() {
          return translators;
     }
}
