package org.frc.team4048.rosnetworktables;

public interface TranslatorTopic {
    /**
     * Start translation process. This should initialize processes and register with the server, etc.
     */
    void start();

    /**
     * Shutdown the registrations and release any resources.
     */
    void stop();
}
