package edu.umb.cs681.hw12;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class DepositRunnable implements Runnable {
    private BankAccount account;
    AtomicBoolean done = new AtomicBoolean(false);

    public DepositRunnable(BankAccount account) {
        this.account = account;
    }

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                account.deposit(100);
                Thread.sleep(Duration.ofSeconds(2));

                if (done.compareAndSet(true, false)) {
                    break;
                }
            }
        } catch (InterruptedException exception) {
        }
    }
}
