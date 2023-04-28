package edu.umb.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class Aircraft {
    private Position position;
    private static ReentrantLock lock = new ReentrantLock();

    public Aircraft(Position pos) {
        this.position = pos;
    }

    public void setPosition(double newLat, double newLong, double newAlt) {
        lock.lock();
        try {
            this.position = this.position.change(newLat, newLong, newAlt);
        } finally {
            lock.unlock();
        }
    }

    public Position getPosition() {
        lock.lock();
        try {
            return this.position;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
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

        System.out.println("Final coordinate: " + a1.getPosition().coordinate());
    }
}
