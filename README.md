# RosNetworkTables
ROS &lt;--> Network Tables bridge for localization 

The repo contains a standalone tool that can be used to translate NetworkTables messages to ROS and back.
The tool is packaged into an Uber-jar that contains the code, all dependencies and the JNI libraries needed 
for NetworkTables to run.

## Build
Run `./gradlew jar` from the project's top level directory.
The created `build/libs/RosNetworkTables-1.0-SNAPSHOT.jar` can then be copied to the target location.

## Run
`java -jar build/libs/RosNetworkTables-1.0-SNAPSHOT.jar`

## Test
In order to test the translator, you'd need to run a NetworkTables server and a Ros node.
1. NetworkTables server can be run by opening a WPILib project on localhost and running 
`Simulate Robot Code` from VSCode's "W" tool
2. TBD instructions on how to run Ros node
3. In the project test/java directory there is a test called `NtClientTest`, within which there is a `sendToNetworkTablesTopic`
method. Running that test method (e.g. from IDEA) as a unit test will start a process that publishes values to the NetworkTables server
4. The test server can be reached by opening a connection to `localhost`
5. The table name that the tests publishes to is `TestTopic` and the value is a double with the name `X`

## Docker
### Build
The program is intended to run as a docker container on the target hardware (Raspberry pi).
In order to build the docker image, in a terminal (starting at the project's top level directory):
```
cd Docker
./build.sh
```
This would create an image TAR file (`nt2ros.tar`) that you'd need to deploy over to the target hardware.

### Deploy
1. Copy the tar file to the target hardware (Pi):
```
# Dont forget the ':' at the end of the command!
scp nt2ros.tar pi@<Pi.IP.address>:
```

2. On the target hardware, do:
```
docker load -i nt2ros.tar
```
Clean the filesystem:
```
rm nt2ros.tar
```

### Run
Normally, the program should run when the Pi boots up, but following an installation, you would run it manually:
```
docker run nt2ros
```
The default runtime configuration is set up to run with the Roborio: The IP addresses are hard-coded for the Roborio and the Jetson.
When running in test, you can change the IP addresses like:
```
docker run -e NT_IP=<ip.address> -e ROS_MASTER_URI=<ip.address> -e ROS_IP=<ip.address> nt2ros
```
## Configuring the Pi
### Make file system read-write
1. Launch the WPILIB GUI
2. Make the system "Writable"
3. SSH to the pi and change the mount
```
ssh pi@wpilibpi.local
```
1. Edit and change `/etc/fstab` to remove the "RO" from `/boot` and `/` file systems
2. Edit and remove the two `sudo mount ...` commands from `/etc/bash.bash_logout`
3. Reboot `sudo reboot`

### Install docker
1. Make sure the pi is connected to the network
2. Set the correct date and time:
`sudo date MMDDhhmm`  (For Month Day Hour minute)
3. `sudo apt update`
4. `sudo apt install ca-certificates curl gnupg lsb-release`
5. `sudo mkdir -m 0755 -p /etc/apt/keyrings`
6. `curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg`
7. `echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null`
8. `sudo apt update`   
9. `sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin`   
10. Verify docker is installed: `sudo docker run hello-world`

### Post installation
1. `sudo groupadd docker`
2. `sudo usermod -aG docker pi` 
   (pi is the username used to log in)
3. logout and log back in
4. Verify running without sudo: `docker image ls`

## Update to a new version of wpilib
The wpilib libraries that are required for the docker container build are located in a zip file at the 
`src/main/lib` folder in this repo. When you are upgrading the library for your project do the following:
1. Update the Gradle dependencies in `gradle.properties` (this is needed for the Java portion)
2. Download the new binaries from https://frcmaven.wpi.edu/ui/native/release/edu/wpi/first/wpiutil/wpiutil-cpp into the `lib` directory
3. Change the version of the upzip command in `Docker/build.sh`
4. Rebuild the docker image
