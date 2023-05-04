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
    }
}
