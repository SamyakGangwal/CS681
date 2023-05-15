package edu.umb.cs681.hw14;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExitHandler implements Runnable {
    private AdmissionMonitor monitor;
    private AtomicBoolean done = new AtomicBoolean(false);

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public ExitHandler(AdmissionMonitor monitor){
        this.monitor = monitor;
    }

    public void run() {
        while (true) {
            if (this.done.get()) {
                System.out.println("EXITING!!!!!!!!!!!!!!!");
                break;
            }
            monitor.exit();
        }

        try {
            Thread.sleep(500);
        }

        catch (InterruptedException e) {
            System.out.println("INTERRUPTION!");
        }
    }
}
