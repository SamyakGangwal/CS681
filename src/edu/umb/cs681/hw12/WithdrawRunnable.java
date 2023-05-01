package edu.umb.cs681.hw12;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class WithdrawRunnable implements Runnable {
    private AtomicBoolean done = new AtomicBoolean(false);


    private BankAccount account;

    public WithdrawRunnable(BankAccount account) {
        this.account = account;
    }

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                account.withdraw(100);
                Thread.sleep(Duration.ofSeconds(2));
                if (done.compareAndSet(true, false)) {
                    break;
                }
            }
        } catch (InterruptedException exception) {
        }
    }

}
