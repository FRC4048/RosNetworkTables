package org.frc.team4048.rosnetworktables;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

public class Launcher {
     public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
          try {
               NtRosProxy.get().start();
          } catch (Exception ex) {
               System.err.println("Launcher caught exception: " + ex.getMessage());
               ex.printStackTrace();
               NtRosProxy.get().stop();
          }
     }
}
