package edu.umb.cs681.hw14;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class AdmissionMonitor {
    private int currentVisitors = 0;
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Condition reduceLoad = rwlock.writeLock().newCondition();
    private Condition zeroLoad = rwlock.writeLock().newCondition();

    public void enter() {
        rwlock.writeLock().lock();
        try {
            while (currentVisitors > 10) {
                System.out.println("Too many visitors. Please wait for a while!"); // waiting until
                                                                                   // the
                                                                                   // # of visitors
                                                                                   // goes
                                                                                   // below 10.
                reduceLoad.await();
            }
            currentVisitors++;
            zeroLoad.signalAll();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void exit() {
        rwlock.writeLock().lock();
        try {
            while (currentVisitors == 0) {
                System.out.println("No visitor. Please wait for a while!"); // waiting until
                // the
                // # of visitors
                // goes
                // below 10.
                zeroLoad.await();
            }
            currentVisitors--;
            reduceLoad.signalAll();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {

            rwlock.writeLock().unlock();
        }
    }

    public int countCurrentVisitors() {
        rwlock.readLock().lock();
        try {
            return currentVisitors;
        } finally {
            rwlock.readLock().unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
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
    }
}
