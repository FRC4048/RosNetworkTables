package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import id.jrosclient.JRosClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileParser {
    private static final String commentChar = "//";
    private static final String separatorChar = " ";
    private final InputStream inputFile;
    private List<TopicPair> topicPairList;
    private List<TranslatorTopic> translators;

    public ConfigFileParser(String filePath) {
        inputFile = this.getClass().getClassLoader().getResourceAsStream(filePath);
    }

    public void readTopics() throws IOException, ClassNotFoundException {
        List<TopicPair> topicPairs = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputFile));
        String currentLine;
        int i = -1;
        while ((currentLine = bf.readLine()) != null) {
            i++;
            if (currentLine.isBlank()) continue;
            if (currentLine.startsWith(commentChar)) continue;
            String[] segments = currentLine.split(separatorChar);
            assert segments.length == 3 : "Incorrect Number of Arguments on line " + i;

            Class<? extends TranslatorTopic> classType = Class.forName(segments[2]).asSubclass(TranslatorTopic.class);
            String ntTopicName = segments[0];
            String rosTopicName = segments[1];
            assert (rosTopicName.isBlank() || ntTopicName.isBlank()) : "Invalid RosTopic or NetworkTopic name on line " + i;
            topicPairs.add(new TopicPair(ntTopicName, rosTopicName, classType));
        }
        this.topicPairList = topicPairs;
    }

    public void createTranslators(JRosClient rosClient, NetworkTable table) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<TranslatorTopic> list = new ArrayList<>();
        for (TopicPair pair : topicPairList) {
            list.add((TranslatorTopic) pair.topicType.getConstructors()[0].newInstance(table, pair.ntTopic, rosClient, pair.rosTopic));
        }
        this.translators = list;
    }

    public List<TranslatorTopic> getTranslators() {
        return translators;
    }
}
