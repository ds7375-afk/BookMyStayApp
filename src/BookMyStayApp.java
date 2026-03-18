import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 8: Booking History & Reporting
 * =============================================================================
 */

// 1. DATA MODEL: Represents a confirmed reservation
// (Simplified version of the Reservation class for this use case)
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

// 2. STORAGE CLASS: Maintains the audit trail of confirmed bookings
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        // ArrayList is used to maintain insertion order (chronological history)
        this.confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// 3. SERVICE CLASS: Handles reporting logic (Separated from storage)
class BookingReportService {
    /**
     * Iterates through history and prints a formatted summary.
     */
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        System.out.println("---------------------------");

        List<Reservation> records = history.getConfirmedReservations();

        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Reservation res : records) {
            System.out.println("Guest: " + res.getGuestName() +
                    ", Room Type: " + res.getRoomType());
        }
    }
}

// 4. MAIN CLASS: Application Entry Point
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize Components
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirming bookings (Adding to history)
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Display the Output
        System.out.println("Booking History and Reporting\n");
        reportService.generateReport(history);
    }
}