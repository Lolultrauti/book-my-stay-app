import java.util.HashMap;

// ─── Abstract Room Class ───────────────────────────────────────────────────

/**
 * Abstract base class representing a hotel room.
 * Defines common attributes and enforces a consistent
 * structure across all room types.
 *
 * @author Your Name
 * @version 1.0
 */
abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;
    protected double roomSizeInSqFt;

    /**
     * Constructs a Room with the specified attributes.
     *
     * @param roomType        the type of the room
     * @param numberOfBeds    the number of beds in the room
     * @param pricePerNight   the nightly rate for the room
     * @param roomSizeInSqFt  the size of the room in square feet
     */
    public Room(String roomType, int numberOfBeds, double pricePerNight, double roomSizeInSqFt) {
        this.roomType       = roomType;
        this.numberOfBeds   = numberOfBeds;
        this.pricePerNight  = pricePerNight;
        this.roomSizeInSqFt = roomSizeInSqFt;
    }

    /**
     * Displays the details of the room to the console.
     * Each subclass must provide its own implementation.
     */
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

// ─── Room Inventory ────────────────────────────────────────────────────────

/**
 * RoomInventory manages the availability of all room types
 * using a centralized HashMap as a single source of truth.
 *
 * <p>This replaces the scattered boolean variables from the
 * previous design with a scalable, consistent data structure.
 * Adding a new room type requires only one new entry in the map.</p>
 *
 * @author Your Name
 * @version 1.0
 */
class RoomInventory {

    /**
     * Maps each room type name to its current available count.
     * Provides O(1) average-time lookup and update operations.
     */
    private HashMap<String, Integer> availability;

    /**
     * Constructs a RoomInventory and registers all room types
     * with their initial available counts.
     */
    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room",  2);
    }

    /**
     * Returns the number of available rooms for the given room type.
     *
     * @param roomType the name of the room type to look up
     * @return the number of available rooms, or 0 if the type is not found
     */
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    /**
     * Updates the available count for a given room type.
     * This method acts as a controlled gateway for all inventory changes,
     * preventing direct external modification of the map.
     *
     * @param roomType the name of the room type to update
     * @param count    the new available count
     */
    public void updateAvailability(String roomType, int count) {
        if (availability.containsKey(roomType)) {
            availability.put(roomType, count);
            System.out.println("Inventory updated: " + roomType + " → " + count + " available");
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    /**
     * Displays the current availability of all room types
     * stored in the inventory map.
     */
    public void displayInventory() {
        System.out.println("----------------------------------------");
        System.out.println("         Current Room Inventory         ");
        System.out.println("----------------------------------------");
        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " : " + availability.get(roomType) + " available");
        }
        System.out.println("----------------------------------------");
    }
}

// ─── Application Entry Point ───────────────────────────────────────────────

/**
 * BookMyStayApp serves as the entry point for the BookMyStay application.
 * Demonstrates centralized inventory management using HashMap.
 *
 * @author Your Name
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * Initializes rooms and inventory, performs availability
     * lookups and updates, then displays the final inventory state.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // ── Create room objects ──────────────────────────────────────────
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom  = new SuiteRoom();

        // ── Display room details ─────────────────────────────────────────
        System.out.println("========================================");
        System.out.println("      BookMyStay - Room Listings        ");
        System.out.println("========================================");

        System.out.println();
        singleRoom.displayRoomDetails();

        System.out.println();
        doubleRoom.displayRoomDetails();

        System.out.println();
        suiteRoom.displayRoomDetails();

        // ── Initialize centralized inventory ─────────────────────────────
        System.out.println();
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        // ── Retrieve availability for a specific room type ───────────────
        System.out.println();
        System.out.println("Availability check:");
        System.out.println("Single Room : " + inventory.getAvailability("Single Room") + " available");
        System.out.println("Suite Room  : " + inventory.getAvailability("Suite Room")  + " available");

        // ── Perform controlled updates ───────────────────────────────────
        System.out.println();
        System.out.println("Performing inventory updates...");
        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Double Room", 1);

        // ── Display updated inventory ────────────────────────────────────
        System.out.println();
        inventory.displayInventory();

        System.out.println();
        System.out.println("========================================");
        System.out.println("Application terminated successfully.");
    }
}
