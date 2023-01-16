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

