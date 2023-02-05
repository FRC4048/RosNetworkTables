package org.frc.team4048.rosnetworktables;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

public class Launcher {
     public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
          NtRosProxy.get().start();
     }
}
