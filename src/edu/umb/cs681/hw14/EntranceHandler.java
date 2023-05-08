package edu.umb.cs681.hw14;

public class EntranceHandler implements Runnable {
    private AdmissionMonitor monitor;

    public void run() {
        try {
            monitor.enter();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
