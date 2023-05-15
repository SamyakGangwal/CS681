package edu.umb.cs681.hw17.deadlock;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlockTest {

    @Test
    public void deadlockCheck() {
        // Creating flights and passengers
        Flight flight1 = new Flight("ABC123");
        Flight flight2 = new Flight("XYZ789");

        Passenger passenger1 = new Passenger("John");
        Passenger passenger2 = new Passenger("Emily");

        // Thread 1 - John reserving seats on flight1 and flight2
        Thread thread1 = new Thread(() -> passenger1.reserveSeat(flight1, flight2));

        // Thread 2 - Emily reserving seats on flight2 and flight1
        Thread thread2 = new Thread(() -> passenger2.reserveSeat(flight2, flight1));

        // Start both threads
        thread1.start();
        thread2.start();

        // I have added this interrupt to purposely end the program
        thread1.interrupt();
        thread2.interrupt();

        assertTrue(thread1.isInterrupted());
    }
}
