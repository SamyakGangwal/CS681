package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestHandler implements Runnable {
    AtomicBoolean done = new AtomicBoolean(false);

    private static int getRandomNumber() {
        return (int) Math.round(Math.random());
    }

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        AccessCounter accessCounter = AccessCounter.getAc();
        int idx = RequestHandler.getRandomNumber();

        Path path;
        int count = 0;

        while (true) {
            if (done.get()) {
                System.out.println("Done!");
                break;
            }

            if (idx == 0) {
                path = Paths.get("D:/umass_boston/cs_681/CS681/assets/a.html");
            } else {
                path = Paths.get("D:/umass_boston/cs_681/CS681/assets/b.html");
            }

            accessCounter.increment(path);
            count = accessCounter.getCount(path);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                continue;
            }
        }

    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        RequestHandler[] runnables = new RequestHandler[10];


        for (int i = 0; i < 10; i++) {
            runnables[i] = new RequestHandler();

            threads[i] = new Thread(runnables[i]);
            threads[i].start();
            runnables[i].setDone();
            threads[i].interrupt();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("THREAD INTERRUPTION DETECTED!");
            }
        }
        AccessCounter ac = AccessCounter.getAc();

        ac.getPathMap().entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

    }

}
