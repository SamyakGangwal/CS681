package edu.umb.cs681.hw12;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class ThreadSafeBankAccount2Test {
    @Test
    public void interruptBankAccount() {
        ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
        int totalThreads = 2;
        Thread[] t1 = new Thread[totalThreads];
        Thread[] t2 = new Thread[totalThreads];

        DepositRunnable[] dr = new DepositRunnable[totalThreads];
        WithdrawRunnable[] wr = new WithdrawRunnable[totalThreads];

        for (int i = 0; i < totalThreads; i++) {
            dr[i] = new DepositRunnable(bankAccount);
            wr[i] = new WithdrawRunnable(bankAccount);
            t1[i] = new Thread(dr[i]);
            t2[i] = new Thread(wr[i]);
            t1[i].start();
            t2[i].start();
        }

        for (int i = 0;i < totalThreads;i ++) {
            dr[i].setDone();
            wr[i].setDone();

            t1[i].interrupt();
            t2[i].interrupt();
            try {
                t1[i].join();
                t2[i].join();
            } catch (InterruptedException e) {
                System.out.println("THREAD INTERRUPTION DETECTED!");
            }
        }

        for (int i = 0;i < totalThreads;i ++) {
            assertFalse(t1[i].isAlive());
        }
    }
}
