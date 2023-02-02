package org.frc.team4048.rosnetworktables;

public class Launcher {
     public static void main(String[] args) throws InterruptedException {
          NtRosProxy.get().start();
     }
}
