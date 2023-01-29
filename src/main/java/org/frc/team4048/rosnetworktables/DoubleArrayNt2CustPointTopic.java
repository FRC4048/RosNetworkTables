package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import geometry_msgs.*;

import java.util.Arrays;

public class DoubleArrayNt2CustPointTopic extends NtToRTopic<double[], Point> {
    public DoubleArrayNt2CustPointTopic(NetworkTable table, String topic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getDoubleArrayTopic(topic).subscribe(new double[]{}),node.createPublisher(rosTopic,Point._TYPE));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        double[] value = event.valueData.value.getDoubleArray();
        System.out.println("Received double " + Arrays.toString(value));
        if (getRosPublisher() == null) System.out.println("Cant Publish " + Arrays.toString(value) + " Because publisher is null");
        getRosPublisher().publish(populateMessage(value,getRosPublisher().newMessage()));
    }

    /**
     *
     * @param value double array into format of [0]=AccelerationX, [1]=AccelerationY, [2]=AccelerationZ
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Point populateMessage(double[] value, Point emptyMessage) {
        emptyMessage.setX(value[0]); // x pos value
        emptyMessage.setY(value[1]);// y pose value
        emptyMessage.setZ(value[2]);// yaw value
        return emptyMessage;
    }
}
