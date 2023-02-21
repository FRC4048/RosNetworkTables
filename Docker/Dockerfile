
#
# Docker image file for the NT <--> Ros translator
#

FROM azul/zulu-openjdk:17

# Default value for NT_IP is the robot roborio
ENV NT_IP=10.40.48.2
# Default value for the ros server is the Jetson
ENV ROS_MASTER_URI=http://10.40.48.95
# Default value for the ros server (this host) is the Pi
ENV ROS_IP=10.40.48.94

WORKDIR nt2ros
# contents is where all the contents resides on the host
COPY contents .
# Parameters and command to run when the container is run
ENTRYPOINT NT_IP=${NT_IP} ROS_IP=${ROS_IP} ROS_MASTER_URI=${ROS_MASTER_URI} java -jar RosNetworkTables.jar
