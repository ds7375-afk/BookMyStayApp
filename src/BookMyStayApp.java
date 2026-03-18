import java.util.*;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 10: Booking Cancellation & Inventory Rollback
 * =============================================================================
 */

// 1. SERVICE CLASS: Manages the reversal of booking state
class CancellationService {
    // Stack stores released room IDs to be reused (LIFO Rollback)
    private Stack<String> releasedRooms;
    private Map<String, String> activeBookings; // Mock storage: ReservationID -> RoomID
    private int inventoryCount;

    public CancellationService(int initialInventory) {
        this.releasedRooms = new Stack<>();
        this.activeBookings = new HashMap<>();
        this.inventoryCount = initialInventory;

        // Pre-populating a mock booking for demonstration
        activeBookings.put("RES101", "Room-501");
    }

    /**
     * Processes cancellation by validating the request and rolling back state.
     */
    public void cancelBooking(String reservationId) {
        System.out.println("Processing Cancellation for: " + reservationId);

        // Step 1: Validation - Ensure reservation exists
        if (!activeBookings.containsKey(reservationId)) {
            System.err.println("Error: Reservation " + reservationId + " not found or already cancelled.");
            return;
        }

        // Step 2: Identify the allocated room
        String roomId = activeBookings.remove(reservationId);

        // Step 3: Stack-based Rollback (LIFO)
        // Pushing the room ID back to the pool
        releasedRooms.push(roomId);

        // Step 4: Inventory Restoration
        inventoryCount++;

        System.out.println("Success: " + roomId + " has been released.");
        System.out.println("Updated Inventory Count: " + inventoryCount);
        System.out.println("Available Rooms in Rollback Pool: " + releasedRooms);
    }

    public int getInventoryCount() { return inventoryCount; }
}

// 2. MAIN CLASS: Entry point to demonstrate the rollback flow
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize with 10 rooms (assuming 1 is already taken by RES101)
        CancellationService service = new CancellationService(9);

        System.out.println("Booking Cancellation & Inventory Rollback");
        System.out.println("------------------------------------------");

        // Scenario 1: Valid Cancellation
        service.cancelBooking("RES101");

        System.out.println("------------------------------------------");

        // Scenario 2: Invalid Cancellation (Already cancelled or non-existent)
        service.cancelBooking("RES101");

        System.out.println("------------------------------------------");

        // Scenario 3: System Stability Check
        System.out.println("Final System Inventory State: " + service.getInventoryCount());
    }
}