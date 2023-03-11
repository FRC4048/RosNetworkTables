# RosNetworkTables
ROS &lt;--> Network Tables bridge for localization 

The repo contains a standalone tool that can be used to translate NetworkTables messages to ROS and back.
The tool is packaged into an Uber-jar that contains the code, all dependencies and the JNI libraries needed 
for NetworkTables to run.
<H2> Build </H2>
`./gradlew jar` from the project's top level directory.
The created `build/libs/RosNetworkTables-1.0-SNAPSHOT.jar` can then be copied to the target location.
<H2>Run</H2>
`java -jar build/libs/RosNetworkTables-1.0-SNAPSHOT.jar`
<H2>Test</H2>
In order to test the translator, you'd need to run a NetworkTables server and a Ros node.
 * NetworkTables server can be run by opening a WPILib project on localhost and running 
`Simulate Robot Code` from VSCode's "W" tool
 * TBD instructions on how to run Ros node
 * In the project test/java directory there is a test called `NtClientTest`, within which there is a `sendToNetworkTablesTopic`
method. Running that test method (e.g. from IDEA) as a unit test will start a process that publishes values to the NetworkTables server
 * The test server can be reached by opening a connection to `localhost`
 * The table name that the tests publishes to is `TestTopic` and the value is a double with the name `X`

<H2>Docker</H2>
<H3>Build</H3>
The program is intended to run as a docker container on the target hardware (Raspberry pi).
In order to build the docker image, in a terminal (starting at the project's top level directory):
```
cd Docker
./build.sh
```
This would create an image TAR file (`nt2ros.tar`) that you'd need to deploy over to the target hardware.

<H3>Deploy</H3>
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
<H3>Run</H3>
Normally, the program should run when the Pi boots up, but following an installation, you would run it manually:
```
docker run nt2ros
```
The default runtime configuration is set up to run with the Roborio: The IP addresses are hard-coded for the Roborio and the Jetson.
When running in test, you can change the IP addresses like:
```
docker run -e NT_IP=<ip.address> -e ROS_MASTER_URI=<ip.address> -e ROS_IP=<ip.address> nt2ros
```
<H2>Configuring the Pi</H2>
<H3>Make file system read-write</H3>
1. Launch the WPILIB GUI
2. Make the system "Writable"
3. SSH to the pi and change the mount
```
ssh pi@wpilibpi.local
```
4. Edit and change `/etc/fstab` to remove the "RO" from `/boot` and `/` file systems
5. Edit and remove the two `sudo mount ...` commands from `/etc/bash.bash_logout`
6. Reboot `sudo reboot`

<H3>Install docker</H3>
1. Make sure the pi is connected to the network
2. Set the correct date and time:
`sudo date MMDDhhmm`  (For Month Day Hour minute)
3. `sudo apt update`
4. `sudo apt install ca-certificates curl gnupg lsb-release`
5. `sudo mkdir -m 0755 -p /etc/apt/keyrings`
6. `curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg`
7. `echo \
   "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
   $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null`
8. `sudo apt update`   
9. `sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin`   
10. Verify docker is installed: `sudo docker run hello-world`

<H3>Post installation</H3>
1. `sudo groupadd docker`
2. `sudo usermod -aG docker pi` 
   (pi is the username used to log in)
3. logout and log back in
4. Verify running without sudo: `docker image ls`
