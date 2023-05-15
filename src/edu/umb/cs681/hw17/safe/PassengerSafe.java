package edu.umb.cs681.hw17.safe;

public class PassengerSafe {
    private String name;

    public PassengerSafe(String name) {
        this.name = name;
    }

    public void confirmReservation() {
        System.out.println(name + " has confirmed the reservation.");
    }

    public String getName() {
        return name;
    }

    public void reserveSeat(FlightSafe flight1, FlightSafe flight2) {
        FlightSafe firstFlight = flight1;
        FlightSafe secondFlight = flight2;

        // Sort the flights based on their identifiers to ensure consistent order of lock acquisition
        if (flight1.getFlightNumber().compareTo(flight2.getFlightNumber()) > 0) {
            firstFlight = flight2;
            secondFlight = flight1;
        }

        boolean acquiredLock1 = false;
        boolean acquiredLock2 = false;

        try {
            acquiredLock1 = firstFlight.getLock().tryLock();
            acquiredLock2 = secondFlight.getLock().tryLock();

            if (acquiredLock1 && acquiredLock2) {
                System.out.println(name + " is reserving a seat on Flight " + firstFlight.getFlightNumber());
                firstFlight.reserveSeat(this);

                System.out.println(name + " is reserving a seat on Flight " + secondFlight.getFlightNumber());
                secondFlight.reserveSeat(this);
            }
        } finally {
            if (acquiredLock1) {
                firstFlight.getLock().unlock();
            }
            if (acquiredLock2) {
                secondFlight.getLock().unlock();
            }
        }
    }
}

