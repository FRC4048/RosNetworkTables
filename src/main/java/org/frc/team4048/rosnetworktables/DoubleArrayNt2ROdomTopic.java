package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import geometry_msgs.*;
import std_msgs.Float64MultiArray;
import nav_msgs.Odometry;

import java.util.Arrays;

public class DoubleArrayNt2ROdomTopic extends NtToRTopic<double[], Odometry> {
    public DoubleArrayNt2ROdomTopic(NetworkTable table, String topic, String rosTopic, RosNode node) {
        super(table.getInstance(), table.getDoubleArrayTopic(topic).subscribe(new double[]{}),node.createPublisher(rosTopic,Odometry._TYPE));
    }

    @Override
    protected void publishToRos(NetworkTableEvent event) {
        double[] value = event.valueData.value.getDoubleArray();
        System.out.println("Received double " + Arrays.toString(value));
        if (getRosPublisher() == null) System.out.println("Cant Publish " + Arrays.toString(value) + " Because publisher is null");
        getRosPublisher().publish(populateMessage(value,getRosPublisher().newMessage()));
    }

    /**
     * X, Y, or Z (Pitch, Roll, or Yaw)
     * @param value double array into format of [0]=AccelerationX, [1]=AccelerationY, [2]=AccelerationZ
     * @param emptyMessage empty message Object created by publisher
     * @return message object with given data
     */
    @Override
    protected Odometry populateMessage(double[] value, Odometry emptyMessage) {

        Point point = Main.rosNode.createMessage(Point._TYPE);

        point.setX(0);
        point.setY(0);
        point.setZ(0);

        Pose pose = Main.rosNode.createMessage(Pose._TYPE);


        Quaternion orientation = Main.rosNode.createMessage(Quaternion._TYPE);
        orientation = convertEulerToQuaternion(orientation,value[0],value[1],value[2]);
//        orientation.setX(0);
//        orientation.setY(0);
//        orientation.setZ(0);
//        orientation.setW(0);

        pose.setOrientation(orientation);

        pose.setPosition(point);
        pose.getOrientation();

        Twist twist = Main.rosNode.createMessage(Twist._TYPE);

        Vector3 angularVector = Main.rosNode.createMessage(Vector3._TYPE);
        angularVector.setX(0);
        angularVector.setY(0);
        angularVector.setZ(0);
        twist.setAngular(angularVector);

        Vector3 LinearVector = Main.rosNode.createMessage(Vector3._TYPE);
        LinearVector.setX(0);
        LinearVector.setY(0);
        LinearVector.setZ(0);
        twist.setLinear(LinearVector);

        PoseWithCovariance poseWithCovariance = Main.rosNode.createMessage(PoseWithCovariance._TYPE);
        poseWithCovariance.setPose(pose);
        poseWithCovariance.setCovariance(new double[36]);

        TwistWithCovariance twistWithCovariance = Main.rosNode.createMessage(TwistWithCovariance._TYPE);
        twistWithCovariance.setTwist(twist);
        twistWithCovariance.setCovariance(new double[36]);

        emptyMessage.setPose(poseWithCovariance);
        emptyMessage.setTwist(twistWithCovariance);
        return emptyMessage;
    }

    public Quaternion convertEulerToQuaternion(Quaternion quat,double pitch, double roll, double yaw){
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        quat.setX(cy * cp * sr - sy * sp * cr);
        quat.setY(cy * sp * cr + sy * cp * sr);
        quat.setZ(sy * cp * cr - cy * sp * sr);
        quat.setW(cy * cp * cr + sy * sp * sr);
        return quat;
    }
}
