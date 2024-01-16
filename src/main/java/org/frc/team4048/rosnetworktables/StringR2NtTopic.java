package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.StringPublisher;
import id.jrosclient.JRosClient;
import id.jrosmessages.std_msgs.StringMessage;

public class StringR2NtTopic extends R2NtTopic<StringMessage> {

    public StringR2NtTopic(NetworkTable table, String ntTopic, JRosClient client, String rosTopic) {
        super(client, StringMessage.class, rosTopic,
                table.getStringTopic(ntTopic).publish());
    }

    @Override
    protected void publishToNt(StringMessage value) {
        narrow().set(value.data);
    }

    public StringPublisher narrow() {
        return (StringPublisher) this.getNtPublisher();
    }
}
