package org.frc.team4048.rosnetworktables.ros;

import org.ros.internal.message.Message;

public interface MessageEvent<T extends Message> {
     void create(T message);
}
