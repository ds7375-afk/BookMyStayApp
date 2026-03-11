abstract class Room {

    // Number of beds available in the room
    protected int numberOfBeds;

    // Total size of the room in square feet
    protected int squareFeet;

    // Price charged per night
    protected double pricePerNight;

    // Constructor
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Room Size: " + squareFeet + " sqft");
        System.out.println("Price per night: ₹" + pricePerNight);
    }

    // Abstract method
    public abstract void roomType();
}

// Child class 1
class DeluxeRoom extends Room {

    public DeluxeRoom(int numberOfBeds, int squareFeet, double pricePerNight) {
        super(numberOfBeds, squareFeet, pricePerNight);
    }

    public void roomType() {
        System.out.println("Room Type: Deluxe Room");
    }
}

// Child class 2
class SuiteRoom extends Room {

    public SuiteRoom(int numberOfBeds, int squareFeet, double pricePerNight) {
        super(numberOfBeds, squareFeet, pricePerNight);
    }

    public void roomType() {
        System.out.println("Room Type: Suite Room");
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Room deluxe = new DeluxeRoom(2, 350, 4500);
        Room suite = new SuiteRoom(3, 600, 8000);

        System.out.println("---- Deluxe Room Details ----");
        deluxe.roomType();
        deluxe.displayRoomDetails();

        System.out.println();

        System.out.println("---- Suite Room Details ----");
        suite.roomType();
        suite.displayRoomDetails();
    }
}