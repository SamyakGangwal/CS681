package edu.umb.cs681.hw18;

import java.nio.file.Path;
import java.nio.file.Paths;
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

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[15];
        RequestHandler[] runnables = new RequestHandler[15];


        for (int i = 0; i < 15; i++) {
            runnables[i] = new RequestHandler();

            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }

        Thread.sleep(3000);

        for (int i = 0; i < 15; i++) {
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
