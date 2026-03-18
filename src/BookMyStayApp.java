import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 12: Data Persistence & System Recovery
 * =============================================================================
 */

// 1. DATA MODEL: Must implement Serializable to be saved to disk
class BookingRecord implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private String guestName;
    private String roomType;

    public BookingRecord(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}

// 2. SYSTEM STATE: The object containing everything we want to persist
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<BookingRecord> history = new ArrayList<>();
    public int remainingInventory = 10;
}

// 3. PERSISTENCE SERVICE: Handles File I/O operations
class PersistenceService {
    private static final String FILE_NAME = "hotel_data.ser";

    /**
     * Serializes the HotelState object and writes it to a file.
     */
    public void saveState(HotelState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println(">>> System state saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    /**
     * Reads the file and Deserializes it back into a HotelState object.
     */
    public HotelState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println(">>> No saved state found. Starting fresh.");
            return new HotelState();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println(">>> Recovering system state from " + FILE_NAME + "...");
            return (HotelState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed: " + e.getMessage());
            return new HotelState(); // Return empty state if file is corrupted
        }
    }
}

// 4. MAIN CLASS: Demonstrates the Save/Restart/Recover flow
public class BookMyStayApp {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();

        // --- STEP 1: Simulate System Startup & Recovery ---
        HotelState currentState = persistence.loadState();

        System.out.println("Current Inventory: " + currentState.remainingInventory);
        System.out.println("Current History Size: " + currentState.history.size());
        for (BookingRecord br : currentState.history) System.out.println(" - " + br);

        // --- STEP 2: Simulate New Business Activity ---
        System.out.println("\nAdding a new booking for 'Dakshin'...");
        currentState.history.add(new BookingRecord("Dakshin", "Suite"));
        currentState.remainingInventory--;

        // --- STEP 3: Simulate System Shutdown & Persistence ---
        System.out.println("\nShutting down system...");
        persistence.saveState(currentState);

        System.out.println("------------------------------------------");
        System.out.println("RUN THE PROGRAM AGAIN to see the recovered data!");
    }
}