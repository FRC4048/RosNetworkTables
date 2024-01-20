package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import id.jrosclient.JRosClient;
import id.jrosmessages.std_msgs.StringMessage;


public class StringNt2RTopic extends NtToRTopic<String, StringMessage> {
    public StringNt2RTopic(NetworkTable table, String ntTopic, JRosClient rosClient, String rosTopic) {
        super(table.getInstance(), table.getStringTopic(ntTopic).subscribe(""),
                createRosPublisher(rosClient, StringMessage.class, rosTopic));
    }

    @Override
    protected StringMessage populateMessage(String value) {
        return new StringMessage().withData(value);
    }
}
