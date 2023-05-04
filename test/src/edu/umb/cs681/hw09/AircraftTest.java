package edu.umb.cs681.hw09;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class AircraftTest {
    @Test
    public void multiplePositions() {
        Position p1 = new Position(45.0, 45.0, 45.0);

        Aircraft a1 = new Aircraft(p1);

        Thread t1 = new Thread(() -> {
                a1.setPosition(Math.random() * 2, Math.random() * 2, Math.random() * 2);
        });

        Thread t2 = new Thread(() -> {
                System.out.println(a1.getPosition());
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final coordinate: " + a1.getPosition().coordinate());

        assertNotEquals(45, a1.getPosition().latitude());
        assertNotEquals(45, a1.getPosition().longitude());
        assertNotEquals(45, a1.getPosition().altitude());
    }
}
