package edu.umb.cs681.hw17.safe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeadlockTest {
    @Test
    public void checkDeadlock() {
        // Creating flights and passengers
        FlightSafe flight1 = new FlightSafe("ABC123");
        FlightSafe flight2 = new FlightSafe("XYZ789");

        PassengerSafe passenger1 = new PassengerSafe("John");
        PassengerSafe passenger2 = new PassengerSafe("Emily");

        // Thread 1 - John reserving seats on flight1 and flight2
        Thread thread1 = new Thread(() -> passenger1.reserveSeat(flight1, flight2));

        // Thread 2 - Emily reserving seats on flight1 and flight2
        Thread thread2 = new Thread(() -> passenger2.reserveSeat(flight1, flight2));

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check for deadlock
        assertEquals(thread1.getState(), Thread.State.TERMINATED);
        assertEquals(thread2.getState(), Thread.State.TERMINATED);

        System.out.println("No deadlock detected!");
    }
}

