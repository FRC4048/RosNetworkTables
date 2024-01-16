package org.frc.team4048.rosnetworktables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import id.jros2client.JRos2Client;
import id.jros2client.JRos2ClientFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class NtRosProxy {
    private static NtRosProxy instance;

    public synchronized static NtRosProxy get() {
        if (instance == null) instance = new NtRosProxy();
        return instance;
    }

    private JRos2Client rosclient;
    private final String networkTablesIP;
    private Topics topics;
    private NetworkTableInstance ntInstance;
    private NetworkTable ntTable;
    private boolean started = false;

    private NtRosProxy() {
        String networkTablesIP = System.getenv(Constants.NT_IP);
        if (networkTablesIP == null) {
            throw new IllegalArgumentException("Environment Variable not set " + Constants.NT_IP);
        }
        this.networkTablesIP = networkTablesIP;
    }

    //TODO Add timeout
    public void start() throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (started) {
            return;
        }
        started = true;
        initRosNode();
        initNetworkTables();
        initializeTopics();
        topics.start();
    }

    /**
     * TODO call this at some point
     */
    public void stop() {
        topics.stop();
        rosclient.close();
        started = false;
    }

    private void initializeTopics() throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        topics = new Topics();
        ConfigFileParser parser = new ConfigFileParser(Constants.CONFIG_FILE_NAME);
        parser.readTopics();
        parser.createTranslators(rosclient, ntTable);
        topics.withTopics(parser.getTranslators());
    }

    private void initRosNode() {
        rosclient = new JRos2ClientFactory().createClient();
    }

    private void initNetworkTables() {
        ntInstance = NetworkTableInstance.getDefault();
        ntInstance.setServer(networkTablesIP);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
//          inst.setServerTeam(4048);  // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        ntInstance.startClient4("NT to ROS proxy");
        ntTable = ntInstance.getTable(Constants.NT_ROOT);
    }
}
