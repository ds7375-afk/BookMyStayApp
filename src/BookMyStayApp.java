import java.util.LinkedList;
import java.util.Queue;

/**
 * 1. CLASS - Reservation
 * Represents a guest's intent to book a room.
 */
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

/**
 * 2. CLASS - BookingRequestQueue
 * Manages booking requests using a Queue to ensure fair (FIFO) allocation.
 */
class BookingRequestQueue {
    // We use LinkedList because it implements the Queue interface in Java
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the end of the line.
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /**
     * Retrieves and removes the next request in line.
     */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Checks if there are any requests left to process.
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * 3. MAIN CLASS - UseCase5BookingRequestQueue
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Request Queue");
        System.out.println("---------------------");

        // Initialize the queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests (Simulating guests clicking 'Book')
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue (FIFO order preserved)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process the queue in the order they arrived
        while (bookingQueue.hasPendingRequests()) {
            Reservation current = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: " + current.getGuestName() +
                    ", Room Type: " + current.getRoomType());
        }
    }
}