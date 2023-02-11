package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import geometry_msgs.*;

public class DoubleArrayNt2CustomPointTopic extends NtToRTopic<double[], Point> {
    public DoubleArrayNt2CustomPointTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getDoubleArrayTopic(ntTopic).subscribe(new double[]{}),node.createPublisher(rosTopic,Point._TYPE));
    }

    /**
     * Method used to populate message with network tables values to a ROS message
     * @param value double array into format of [0]=AccelerationX, [1]=AccelerationY, [2]=AccelerationZ
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Point populateMessage(double[] value, Point emptyMessage) {
        emptyMessage.setX(value[0]); // x pos value
        emptyMessage.setY(value[1]);// y pose value
        emptyMessage.setZ(value[2]);// yaw value
        System.out.printf("Sent Value x=%s, y=%s, yaw=%s\n",value[0],value[1],value[2]);
        return emptyMessage;
    }
}
