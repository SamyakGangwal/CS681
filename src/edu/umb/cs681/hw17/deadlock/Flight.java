package edu.umb.cs681.hw17.deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class Flight {
    private String flightNumber;
    private ReentrantLock lock = new ReentrantLock();

    public ReentrantLock getLock() {
        return lock;
    }

    public Flight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void reserveSeat(Passenger passenger) {
        lock.lock();
        try {
            System.out.println(passenger.getName() + " is reserving a seat on Flight " + flightNumber);
            Thread.sleep(1000); // Simulating some processing time

            passenger.confirmReservation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
