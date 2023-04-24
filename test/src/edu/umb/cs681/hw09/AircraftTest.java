package edu.umb.cs681.hw09;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class AircraftTest {
	@Test
	public void multiplePositions() {
		Position p1 = new Position(45, 45, 45);

        Aircraft a1 = new Aircraft(p1);

        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 Before: " + a1.getPosition());
            a1.setPosition(60, 65, 70);
            System.out.println("Thread 1 After: " + a1.getPosition());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2 Before: " + a1.getPosition());
            a1.setPosition(20, 15, 40);
            System.out.println("Thread 2 After: " + a1.getPosition());
        });

        Thread t3 = new Thread(() -> {
            System.out.println("Thread 3 Before: " + a1.getPosition());
            a1.setPosition(55, 75, 12);
            System.out.println("Thread 3 After: " + a1.getPosition());
        });

        t1.start();
        t2.start();
        t3.start();

        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotEquals(45,a1.getPosition().latitude());
		assertNotEquals(45, a1.getPosition().longitude());
		assertNotEquals(45, a1.getPosition().altitude());
	}
}
