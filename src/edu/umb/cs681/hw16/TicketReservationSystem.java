package edu.umb.cs681.hw16;

public class TicketReservationSystem {
    private int unreservedSeats;

    public TicketReservationSystem(int unreservedSeats) {
        this.unreservedSeats = unreservedSeats;
    }

    public int getUnreservedSeats() {
        return unreservedSeats;
    }

    public boolean bookSeat() {
        
        if (unreservedSeats > 0) {
            // Simulating some processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            unreservedSeats--;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        final TicketReservationSystem reservationSystem = new TicketReservationSystem(1);

        // Create two threads that attempt to reserve a seat concurrently
        Thread thread1 = new Thread(() -> {
            if (reservationSystem.bookSeat()) {
                System.out.println("Thread 1: Seat reserved successfully.");
            } else {
                System.out.println("Thread 1: Failed to reserve seat.");
            }
        });

        Thread thread2 = new Thread(() -> {
            if (reservationSystem.bookSeat()) {
                System.out.println("Thread 2: Seat reserved successfully.");
            } else {
                System.out.println("Thread 2: Failed to reserve seat.");
            }
        });

        thread1.start();
        thread2.start();
    }
}
