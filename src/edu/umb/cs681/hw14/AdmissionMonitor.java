package edu.umb.cs681.hw14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class AdmissionMonitor {
    private int currentVisitors = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition sufficientFundsCondition = lock.newCondition();
    private Condition belowUpperLimitFundsCondition = lock.newCondition();


    public void enter() throws InterruptedException {
        lock.lock();

        while (currentVisitors > 10) {
            System.out.println("Too many visitors. Please wait for a while!"); // waiting until the
                                                                               // # of visitors goes
                                                                               // below 10.
            Thread.sleep(1000);
        }
        currentVisitors++;
        lock.unlock();
    }

    public void exit() {
        lock.lock();
        currentVisitors--;
        lock.unlock();
    }

    public int countCurrentVisitors() {
        return currentVisitors;
    }
}
