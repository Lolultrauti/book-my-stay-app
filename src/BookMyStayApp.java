/**
 * BookMyStayApp serves as the entry point for the BookMyStay application.
 * Demonstrates object modeling through inheritance and abstraction.
 *
 * @author Your Name
 * @version 1.0
 */

// ─── Abstract Room Class ───────────────────────────────────────────────────

/**
 * Abstract base class representing a hotel room.
 */
abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;
    protected double roomSizeInSqFt;

    public Room(String roomType, int numberOfBeds, double pricePerNight, double roomSizeInSqFt) {
        this.roomType       = roomType;
        this.numberOfBeds   = numberOfBeds;
        this.pricePerNight  = pricePerNight;
        this.roomSizeInSqFt = roomSizeInSqFt;
    }

    public abstract void displayRoomDetails();
}

// ─── Concrete Room Classes ─────────────────────────────────────────────────

/** Represents a Single Room. */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 80.00, 200.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type   : " + roomType);
        System.out.println("Beds        : " + numberOfBeds);
        System.out.println("Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("Price/Night : $" + pricePerNight);
    }
}

/** Represents a Double Room. */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 140.00, 350.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type   : " + roomType);
        System.out.println("Beds        : " + numberOfBeds);
        System.out.println("Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("Price/Night : $" + pricePerNight);
    }
}

/** Represents a Suite Room. */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.00, 750.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type   : " + roomType);
        System.out.println("Beds        : " + numberOfBeds);
        System.out.println("Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("Price/Night : $" + pricePerNight);
    }
}

// ─── Application Entry Point ───────────────────────────────────────────────

/**
 * Main application class for BookMyStay.
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom  = new SuiteRoom();

        boolean isSingleRoomAvailable = true;
        boolean isDoubleRoomAvailable = false;
        boolean isSuiteRoomAvailable  = true;

        System.out.println("========================================");
        System.out.println("      BookMyStay - Room Listings        ");
        System.out.println("========================================");

        System.out.println();
        singleRoom.displayRoomDetails();
        System.out.println("Available   : " + isSingleRoomAvailable);

        System.out.println();
        doubleRoom.displayRoomDetails();
        System.out.println("Available   : " + isDoubleRoomAvailable);

        System.out.println();
        suiteRoom.displayRoomDetails();
        System.out.println("Available   : " + isSuiteRoomAvailable);

        System.out.println();
        System.out.println("========================================");
        System.out.println("Application terminated successfully.");
    }
}