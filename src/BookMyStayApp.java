import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =============================================================================
 * PROJECT: Book My Stay App
 * USE CASE 7: Add-On Service Selection
 * =============================================================================
 */

// 1. DATA MODEL: Represents an optional service (Breakfast, Spa, etc.)
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

// 2. LOGIC MANAGER: Manages the One-to-Many relationship between Reservations and Services
class AddOnServiceManager {
    // Key: Reservation ID (String) | Value: List of selected services
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        this.servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a specific reservation ID.
     * Uses computeIfAbsent to initialize the list if it's the first service added.
     */
    public void addService(String reservationId, AddOnService service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    /**
     * Calculates the sum of all service costs for a specific reservation.
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }
}

// 3. APPLICATION ENTRY POINT: Demonstrates the workflow
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize the manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Define some example services
        AddOnService breakfast = new AddOnService("Breakfast", 500.0);
        AddOnService spa = new AddOnService("Spa", 1000.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 300.0);

        // Scenario: A guest with Reservation ID "Single-1" picks services
        String resId = "Single-1";

        manager.addService(resId, breakfast);
        manager.addService(resId, spa);

        // Calculate and Display Output
        double totalCost = manager.calculateTotalServiceCost(resId);

        System.out.println("Add-On Service Selection");
        System.out.println("-------------------------");
        System.out.println("Reservation ID: " + resId);
        System.out.println("Total Add-On Cost: " + totalCost);

        // Example of a second reservation to show independence
        String resId2 = "Double-102";
        manager.addService(resId2, airportPickup);
        System.out.println("\nReservation ID: " + resId2);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(resId2));
    }
}