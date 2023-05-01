package edu.umb.cs681.hw12;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2 implements BankAccount {
    private double balance = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition sufficientFundsCondition = lock.newCondition();
    private Condition belowUpperLimitFundsCondition = lock.newCondition();

    public void deposit(double amount) {
        lock.lock();
        try {
            System.out.println("Lock obtained");
            System.out.println(
                    Thread.currentThread().threadId() + " (d): current balance: " + balance);
            while (balance >= 300) {
                System.out.println(Thread.currentThread().threadId()
                        + " (d): await(): Balance exceeds the upper limit.");
                belowUpperLimitFundsCondition.await();
            }
            balance += amount;
            System.out.println(Thread.currentThread().threadId() + " (d): new balance: " + balance);
            sufficientFundsCondition.signalAll();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Lock released");
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            System.out.println("Lock obtained");
            System.out.println(
                    Thread.currentThread().threadId() + " (w): current balance: " + balance);
            while (balance <= 0) {
                System.out.println(
                        Thread.currentThread().threadId() + " (w): await(): Insufficient funds");
                sufficientFundsCondition.await();
            }
            balance -= amount;
            System.out.println(Thread.currentThread().threadId() + " (w): new balance: " + balance);
            belowUpperLimitFundsCondition.signalAll();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Lock released");
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
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

    }
}
