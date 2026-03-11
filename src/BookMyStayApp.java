import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Combined implementation of Use Case 3: Centralized Room Inventory Management.
 * This code demonstrates how to use a HashMap to maintain a single source of truth.
 */

// 1. Room Domain Model (Handles characteristics)
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}

// 2. Inventory Controller (Handles availability)
class RoomInventory {
    // Key -> Room type name | Value -> Available room count
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        this.roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Centralizes inventory setup instead of using scattered variables.
     */
    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    /**
     * Returns the current availability map.
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type via controlled method.
     */
    public void updateAvailability(String roomType, int count) {
        if (roomAvailability.containsKey(roomType)) {
            roomAvailability.put(roomType, count);
        }
    }
}

// 3. Main Application Class
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize the centralized inventory component
        RoomInventory inventoryManager = new RoomInventory();

        // Create the list of Room objects (Pricing and Characteristics)
        List<Room> hotelRooms = new ArrayList<>();
        hotelRooms.add(new Room("Single Room", 1, 250, 1500.0));
        hotelRooms.add(new Room("Double Room", 2, 400, 2500.0));
        hotelRooms.add(new Room("Suite Room", 3, 750, 5000.0));

        // Display the Inventory Status as per the screenshot requirements
        System.out.println("Hotel Room Inventory Status");
        System.out.println("---------------------------");

        for (Room room : hotelRooms) {
            String roomType = room.getType();

            // Fetch availability from the centralized HashMap (O(1) lookup)
            int availableRooms = inventoryManager.getRoomAvailability().getOrDefault(roomType, 0);

            System.out.println(roomType + ":");
            System.out.println("Beds: " + room.getBeds());
            System.out.println("Size: " + room.getSize() + " sqft");
            System.out.println("Price per night: " + room.getPrice());
            System.out.println("Available Rooms: " + availableRooms);
            System.out.println();
        }

        // Demonstration of a controlled update
        System.out.println(">>> Action: Booking 1 Suite Room...");
        int currentSuites = inventoryManager.getRoomAvailability().get("Suite Room");
        inventoryManager.updateAvailability("Suite Room", currentSuites - 1);

        System.out.println("Updated Available Suites: " + inventoryManager.getRoomAvailability().get("Suite Room"));
    }
}