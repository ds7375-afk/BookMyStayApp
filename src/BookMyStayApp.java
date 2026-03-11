import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

// 1. Room Domain Model (Static Characteristics)
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

// 2. Centralized Room Inventory (From UC3)
class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 0); // Setting Suite to 0 to test Filtering
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

// 3. Use Case 4: Room Search Service (Read-Only)
class RoomSearchService {
    /**
     * Displays only available rooms.
     * No inventory mutation happens here.
     */
    public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search Results:");
        System.out.println("--------------------");

        // Logic to check and display only if availability > 0
        displayIfAvailable(singleRoom, availability.getOrDefault("Single", 0));
        displayIfAvailable(doubleRoom, availability.getOrDefault("Double", 0));
        displayIfAvailable(suiteRoom, availability.getOrDefault("Suite", 0));
    }

    private void displayIfAvailable(Room room, int count) {
        if (count > 0) {
            System.out.println(room.getType() + " Room:");
            System.out.println("Beds: " + room.getBeds());
            System.out.println("Size: " + room.getSize() + " sqft");
            System.out.println("Price per night: " + room.getPrice());
            System.out.println("Status: " + count + " rooms left\n");
        }
    }
}

// 4. Main Driver Class
public class BookMyStayApp {
    public static void main(String[] args) {
        // Setup Data
        RoomInventory inventory = new RoomInventory();
        Room single = new Room("Single", 1, 250, 1500.0);
        Room doubleR = new Room("Double", 2, 400, 2500.0);
        Room suite = new Room("Suite", 3, 750, 5000.0);

        // Execute Search Service
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, single, doubleR, suite);
    }
}