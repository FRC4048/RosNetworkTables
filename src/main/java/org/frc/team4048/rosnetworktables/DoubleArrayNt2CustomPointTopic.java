package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import geometry_msgs.Point;

public class DoubleArrayNt2CustomPointTopic extends NtToRTopic<double[], Point> {
    public DoubleArrayNt2CustomPointTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getEntry(ntTopic), node.createPublisher(rosTopic, Point._TYPE));
    }

    /**
     * Method used to populate message with network tables values to a ROS message
     *
     * @param value        double array into format of [0]=AccelerationX, [1]=AccelerationY, [2]=AccelerationZ
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
