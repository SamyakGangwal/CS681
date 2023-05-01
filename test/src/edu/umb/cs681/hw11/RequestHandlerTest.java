package edu.umb.cs681.hw11;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RequestHandlerTest {
    @Test
    public void intteruptCountPath() throws InterruptedException {
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

        for (int i = 0; i < 10; i++) {
            assertTrue(threads[i].isInterrupted());
        }
    }
}
