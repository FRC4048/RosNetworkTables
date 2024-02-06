package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.NetworkTable;
import id.jrosclient.JRosClient;

public class RoborioOdometryR2DoubleArrayNtTopic extends R2NtTopic<RoborioOdometryMessage> {
    public static final int POSITION_X = 0;
    public static final int POSITION_Y = 1;
    public static final int ORIENTATION_YAW = 2;

    public RoborioOdometryR2DoubleArrayNtTopic(NetworkTable table, String ntTopic, JRosClient client, String rosTopic) {
        super(client, RoborioOdometryMessage.class, rosTopic,
                table.getDoubleArrayTopic(ntTopic).publish());
    }

    @Override
    protected void publishToNt(RoborioOdometryMessage value) {
        double[] arr = new double[3];
        arr[POSITION_X] = value.x;
        arr[POSITION_Y] = value.y;
        arr[ORIENTATION_YAW] = value.yaw;

        narrow().set(arr);

        // System.out.println("Sent " + value.x + "/" + value.y + "/" + value.yaw);
    }

    public DoubleArrayPublisher narrow() {
        return (DoubleArrayPublisher) this.getNtPublisher();
    }
}
