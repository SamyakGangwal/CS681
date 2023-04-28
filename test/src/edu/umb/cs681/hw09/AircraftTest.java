package edu.umb.cs681.hw09;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class AircraftTest {
    @Test
    public void multiplePositions() {
        Position p1 = new Position(45.0, 45.0, 45.0);

        Aircraft a1 = new Aircraft(p1);

        Thread t1 = new Thread(() -> {
            Position p2 = a1.getPosition();
            a1.setPosition(p2.latitude() + 1, p2.longitude(), p2.altitude());
        });

        Thread t2 = new Thread(() -> {
            Position p2 = a1.getPosition();
            a1.setPosition(p2.latitude(), p2.longitude() + 1, p2.altitude());
        });

        Thread t3 = new Thread(() -> {
            Position p2 = a1.getPosition();
            a1.setPosition(p2.latitude(), p2.longitude(), p2.altitude() + 1);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(46, a1.getPosition().latitude());
        assertEquals(46, a1.getPosition().longitude());
        assertEquals(46, a1.getPosition().altitude());
    }
}
