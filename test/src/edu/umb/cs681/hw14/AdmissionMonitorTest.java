package edu.umb.cs681.hw14;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AdmissionMonitorTest {
    @Test
    public void multiTHreadedVisitors() throws InterruptedException {
        Thread[] th = new Thread[6];
        AdmissionMonitor[] m = new AdmissionMonitor[2];
        EntranceHandler[] entranceHandler = new EntranceHandler[2];
        ExitHandler[] exitHandler = new ExitHandler[2];
        StatsHandler[] statsHandler = new StatsHandler[2];

        m[0] = new AdmissionMonitor();
        m[1] = new AdmissionMonitor();

        entranceHandler[0] = new EntranceHandler(m[0]);
        entranceHandler[1] = new EntranceHandler(m[1]);

        exitHandler[0] = new ExitHandler(m[0]);
        exitHandler[1] = new ExitHandler(m[1]);

        statsHandler[0] = new StatsHandler(m[0]);
        statsHandler[1] = new StatsHandler(m[1]);

        for (int i = 0;i < 2;i ++) {
            th[i] = new Thread(entranceHandler[i]);
        }

        for (int i = 0;i < 2;i ++) {
            th[2 + i] = new Thread(exitHandler[i]);
        }

        for (int i = 0;i < 2;i ++) {
            th[4 + i] = new Thread(statsHandler[i]);
        }

        for (int i = 0;i < 6;i ++) {
            th[i].start();
        }

        Thread.sleep(500);
        entranceHandler[0].setDone();
        entranceHandler[1].setDone();

        exitHandler[0].setDone();
        exitHandler[1].setDone();

        statsHandler[0].setDone();
        statsHandler[1].setDone();

        for (int i = 0;i < 6;i ++) {
            try {
                th[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 6; i++) {
            assertTrue(th[i].getState() == Thread.State.TERMINATED);
        }
    }
}