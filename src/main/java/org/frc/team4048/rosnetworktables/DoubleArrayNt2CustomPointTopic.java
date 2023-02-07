package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import geometry_msgs.Point;

public class DoubleArrayNt2CustomPointTopic extends NtToRTopic<double[], Point> {
    public DoubleArrayNt2CustomPointTopic(NetworkTable table, String ntTopic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getEntry(ntTopic), node.createPublisher(rosTopic, Point._TYPE));
    }

    /**
     * TODO change wrapper to use strings so we can included a frame id in network table message. Once data is in string format we can convert first 3 args to double and then publish a PointStamped to ros, which we can view in rviz
     * Method used to populate message with network tables values to a ROS message
     * @param value double array in format of [0]=XPos, [1]=yPos, [2]=yaw
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
