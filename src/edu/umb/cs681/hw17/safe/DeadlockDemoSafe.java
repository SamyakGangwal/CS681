package edu.umb.cs681.hw17.safe;

public class DeadlockDemoSafe {
    public static void main(String[] args) {
        FlightSafe flight1 = new FlightSafe("ABC123");
        FlightSafe flight2 = new FlightSafe("XYZ789");

        PassengerSafe passenger1 = new PassengerSafe("John");
        PassengerSafe passenger2 = new PassengerSafe("Emily");

        Thread thread1 = new Thread(() -> passenger1.reserveSeat(flight1, flight2));
        Thread thread2 = new Thread(() -> passenger2.reserveSeat(flight1, flight2));

        thread1.start();
        thread2.start();
    }
    
}
