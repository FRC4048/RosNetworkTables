
#
# Docker image file for the NT <--> Ros translator
#

FROM azul/zulu-openjdk:17.0.4.1-jre

# Default value for NT_IP is the robot roborio
ENV NT_IP=10.40.48.2
# PRELOAD wpilib ntcore jni since there is an issue with the jni dynamic load
ENV LD_PRELOAD=wpilib/linux/arm64/shared/libwpiutiljni.so

WORKDIR nt2ros

# Install missing library for network tables
RUN apt update
RUN apt -y install libatomic1

# contents is where all the contents resides on the host
COPY contents .
# Parameters and command to run when the container is run
CMD ["java", "-jar", "RosNetworkTables.jar"]
