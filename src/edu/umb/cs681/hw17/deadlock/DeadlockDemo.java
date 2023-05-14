package edu.umb.cs681.hw17.deadlock;

public class DeadlockDemo {
    public static void main(String[] args) {
        Flight flight1 = new Flight("ABC123");
        Flight flight2 = new Flight("XYZ789");

        Passenger passenger1 = new Passenger("John");
        Passenger passenger2 = new Passenger("Emily");

        Thread thread1 = new Thread(() -> passenger1.reserveSeat(flight1, flight2));
        Thread thread2 = new Thread(() -> passenger2.reserveSeat(flight2, flight1));

        thread1.start();
        thread2.start();
    }
}

