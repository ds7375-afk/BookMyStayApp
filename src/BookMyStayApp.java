import java.util.Scanner;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 9: Error Handling & Validation
 * =============================================================================
 */

// 1. CUSTOM EXCEPTION: Represents domain-specific booking errors
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// 2. MOCK INVENTORY: Used for validation logic
class RoomInventory {
    public boolean isValidType(String roomType) {
        // Simplified check: only allows specific types (case-sensitive for this example)
        return roomType.equals("Single") || roomType.equals("Double") || roomType.equals("Suite");
    }
}

// 3. VALIDATOR: Centralizes all rules to prevent data corruption
class ReservationValidator {
    /**
     * Validates input before any processing happens.
     * @throws InvalidBookingException if rules are violated.
     */
    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected: " + roomType);
        }
    }
}

// 4. MAIN CLASS: Demonstrates Graceful Failure Handling
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Validation System");
        System.out.println("-------------------------");

        Scanner scanner = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            // Step 1: Collect Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Step 2: Validate (Fail-Fast)
            // If this fails, the code jumps straight to the catch block
            validator.validate(name, type, inventory);

            // Step 3: Success Path
            System.out.println("Validation successful! Proceeding with booking for " + name);

        } catch (InvalidBookingException e) {
            // Step 4: Graceful Failure
            // The system doesn't crash; it just informs the user.
            System.err.println("Booking failed: " + e.getMessage());
        } finally {
            // Ensure resources are closed regardless of success or failure
            scanner.close();
            System.out.println("-------------------------");
            System.out.println("System state remains stable.");
        }
    }
}