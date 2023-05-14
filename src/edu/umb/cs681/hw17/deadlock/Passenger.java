package edu.umb.cs681.hw17.deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Passenger {
    private String name;
    private ReentrantLock lock = new ReentrantLock();

    public Passenger(String name) {
        this.name = name;
    }

    public void confirmReservation() {
        System.out.println(name + " has confirmed the reservation.");
    }

    public String getName() {
        return name;
    }

    public void reserveSeat(Flight flight1, Flight flight2) {
        boolean acquiredLock1 = false;
        boolean acquiredLock2 = false;

        try {
            acquiredLock1 = lock.tryLock();
            acquiredLock2 = flight2.getLock().tryLock();

            if (acquiredLock1 && acquiredLock2) {
                System.out.println(name + " is reserving a seat on Flight " + flight1.getFlightNumber());
                flight1.reserveSeat(this);

                System.out.println(name + " is reserving a seat on Flight " + flight2.getFlightNumber());
                flight2.reserveSeat(this);
            }
        } finally {
            if (acquiredLock1) {
                lock.unlock();
            }
            if (acquiredLock2) {
                flight2.getLock().unlock();
            }
        }
    }
}
