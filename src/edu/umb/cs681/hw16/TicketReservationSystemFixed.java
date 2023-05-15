package edu.umb.cs681.hw16;

import java.util.concurrent.locks.ReentrantLock;

public class TicketReservationSystemFixed {
    private int unreservedSeats;
    private static ReentrantLock lock = new ReentrantLock();

    public int getUnreservedSeats() {
        lock.lock();
        try {
            return unreservedSeats;
        } finally {
            lock.unlock();
        }
    }

    public TicketReservationSystemFixed(int unreservedSeats) {
        this.unreservedSeats = unreservedSeats;
    }

    public boolean bookSeat() {
        lock.lock();

        try {
            if (unreservedSeats > 0) {
                // Simulating some processing time
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                unreservedSeats--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final TicketReservationSystemFixed reservationSystem = new TicketReservationSystemFixed(1);

        // Create two threads that attempt to reserve a seat concurrently
        Thread thread1 = new Thread(() -> {
            if (reservationSystem.bookSeat()) {
                System.out.println("Thread 1: Seat reserved successfully.");
            } else {
                System.out.println("Thread 1: Failed to reserve seat.");
            }
        });

        Thread thread2 = new Thread(() -> {
            if (reservationSystem.bookSeat()) {
                System.out.println("Thread 2: Seat reserved successfully.");
            } else {
                System.out.println("Thread 2: Failed to reserve seat.");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
