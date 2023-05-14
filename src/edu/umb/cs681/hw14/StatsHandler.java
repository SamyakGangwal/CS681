package edu.umb.cs681.hw14;

import java.util.concurrent.atomic.AtomicBoolean;

public class StatsHandler implements Runnable {
    private AdmissionMonitor monitor;
    private AtomicBoolean done = new AtomicBoolean(false);

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public StatsHandler(AdmissionMonitor monitor) {
        this.monitor = monitor;
    }

    public void run() {
        while (true) {
            if (done.get()) {
                break;
            }
            monitor.countCurrentVisitors();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED!");
            }
        }
    }
}
