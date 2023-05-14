package edu.umb.cs681.hw16;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TicketReservationSystemSafeTest {
    private static final int AVAILABLE_SEATS = 1;
    private static final int NUM_THREADS = 2;

    @Test
    public void testRaceCondition() throws InterruptedException {
        TicketReservationSystemFixed reservationSystem = new TicketReservationSystemFixed(AVAILABLE_SEATS);

        // Create multiple threads that attempt to reserve a seat concurrently
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                if (reservationSystem.bookSeat()) {
                    System.out.println("Seat reserved successfully.");
                    System.out.println(reservationSystem.getUnreservedSeats());
                } else {
                    System.out.println("Failed to reserve seat.");
                }
            });
        }

        // Start all the threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Check if the race condition occurred
        int expectedSeats = AVAILABLE_SEATS - NUM_THREADS;

        assertNotEquals(expectedSeats, reservationSystem.getUnreservedSeats(), "Race condition occurred!");
    }
}

