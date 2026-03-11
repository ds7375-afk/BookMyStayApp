import java.util.*;

// 1. Domain Models from previous use cases
class Room {
    private String type;
    public Room(String type) { this.type = type; }
    public String getType() { return type; }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// 2. Room Inventory (State Holder)
class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();

    public RoomInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }

    public void updateAvailability(String type, int count) {
        roomAvailability.put(type, count);
    }
}

// 3. Use Case 6: Room Allocation Service
class RoomAllocationService {
    // Stores ALL allocated IDs to prevent duplicates globally
    private Set<String> allocatedRoomIds = new HashSet<>();
    // Maps Room Type -> Set of specific IDs (e.g., "Single" -> {"Single-1", "Single-2"})
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();

    /**
     * Confirms booking and updates inventory atomically.
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();
        int available = inventory.getRoomAvailability().getOrDefault(type, 0);

        if (available > 0) {
            // 1. Generate unique ID
            String roomId = generateRoomId(type);

            // 2. Record allocation in Sets
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);

            // 3. Decrement inventory immediately
            inventory.updateAvailability(type, available - 1);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                    ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName() + ": No " + type + " rooms available.");
        }
    }

    private String generateRoomId(String type) {
        // Logic: Count existing rooms of this type and add 1
        int nextNumber = assignedRoomsByType.getOrDefault(type, new HashSet<>()).size() + 1;
        return type + "-" + nextNumber;
    }
}

// 4. Main Driver
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");
        System.out.println("--------------------------");

        // Setup
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        Queue<Reservation> requestQueue = new LinkedList<>();

        // Add Requests (FIFO)
        requestQueue.offer(new Reservation("Abhi", "Single"));
        requestQueue.offer(new Reservation("Subha", "Single"));
        requestQueue.offer(new Reservation("Vanmathi", "Suite"));

        // Process until queue is empty
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll();
            allocationService.allocateRoom(request, inventory);
        }
    }
}