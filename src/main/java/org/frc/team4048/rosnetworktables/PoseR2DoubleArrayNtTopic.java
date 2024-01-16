package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.NetworkTable;
import id.jrosclient.JRosClient;
import id.jrosmessages.geometry_msgs.PoseMessage;

public class PoseR2DoubleArrayNtTopic extends R2NtTopic<PoseMessage> {
    public static final int POSITION_X = 0;
    public static final int POSITION_Y = 1;
    public static final int POSITION_Z = 2;
    public static final int ORIENTATION_X = 3;
    public static final int ORIENTATION_Y = 4;
    public static final int ORIENTATION_Z = 5;
    public static final int ORIENTATION_W = 6;

    public PoseR2DoubleArrayNtTopic(NetworkTable table, String ntTopic, JRosClient client, String rosTopic) {
        super(client, PoseMessage.class, rosTopic,
                table.getDoubleArrayTopic(ntTopic).publish());
    }

    @Override
    protected void publishToNt(PoseMessage value) {
        double[] arr = new double[7];
        arr[POSITION_X] = value.position.x;
        arr[POSITION_Y] = value.position.y;
        arr[POSITION_Z] = value.position.z;
        arr[ORIENTATION_X] = value.orientation.x;
        arr[ORIENTATION_Y] = value.orientation.y;
        arr[ORIENTATION_Z] = value.orientation.z;
        arr[ORIENTATION_W] = value.orientation.w;

        narrow().set(arr);
    }

    public DoubleArrayPublisher narrow() {
        return (DoubleArrayPublisher) this.getNtPublisher();
    }
}
