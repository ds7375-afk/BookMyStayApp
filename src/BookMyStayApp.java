import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 11: Concurrent Booking Simulation (Thread Safety)
 * =============================================================================
 */

// 1. SHARED RESOURCE: Manages inventory with Thread-Safe methods
class BookingSystem {
    private int availableRooms = 1; // Only 1 room to test race conditions

    /**
     * The 'synchronized' keyword ensures that only one thread can
     * execute this method at a time, preventing double-booking.
     */
    public synchronized void bookRoom(String guestName) {
        System.out.println(guestName + " is attempting to book...");

        if (availableRooms > 0) {
            // Simulate a small delay in processing to highlight potential race conditions
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            availableRooms--;
            System.out.println("SUCCESS: Room allocated to " + guestName);
        } else {
            System.out.println("FAILURE: No rooms left for " + guestName);
        }
    }

    public int getAvailableRooms() { return availableRooms; }
}

// 2. ACTOR: Represents a Guest making a request on a separate thread
class GuestThread extends Thread {
    private BookingSystem system;
    private String guestName;

    public GuestThread(BookingSystem system, String guestName) {
        this.system = system;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        system.bookRoom(guestName);
    }
}

// 3. MAIN CLASS: Simulates simultaneous requests
public class BookMyStayApp {
    public static void main(String[] args) {
        // Shared system between all guests
        BookingSystem sharedSystem = new BookingSystem();

        System.out.println("Concurrent Booking Simulation");
        System.out.println("Initial Inventory: " + sharedSystem.getAvailableRooms());
        System.out.println("------------------------------------------");

        // Simulate 3 guests hitting the server at the exact same time
        GuestThread guest1 = new GuestThread(sharedSystem, "Alice");
        GuestThread guest2 = new GuestThread(sharedSystem, "Bob");
        GuestThread guest3 = new GuestThread(sharedSystem, "Charlie");

        // Start all threads simultaneously
        guest1.start();
        guest2.start();
        guest3.start();

        // Wait for all threads to finish before printing final state
        try {
            guest1.join();
            guest2.join();
            guest3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------");
        System.out.println("Final Inventory Count: " + sharedSystem.getAvailableRooms());
    }
}