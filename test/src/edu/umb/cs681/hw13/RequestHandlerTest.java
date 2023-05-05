package edu.umb.cs681.hw13;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RequestHandlerTest {
    @Test
    public void intteruptCountPath() throws InterruptedException {
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

        for (int i = 0; i < 15; i++) {
            assertTrue(threads[i].isInterrupted());
        }
    }
}
