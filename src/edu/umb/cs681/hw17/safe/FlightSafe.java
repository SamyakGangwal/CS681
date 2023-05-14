package edu.umb.cs681.hw17.safe;

import java.util.concurrent.locks.ReentrantLock;

public class FlightSafe {
    private String flightNumber;
    private ReentrantLock lock = new ReentrantLock();

    public FlightSafe(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void reserveSeat(PassengerSafe passenger) {
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

    public ReentrantLock getLock() {
        return lock;
    }
}

