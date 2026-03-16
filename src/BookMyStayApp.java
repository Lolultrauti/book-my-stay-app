import java.util.HashMap;
import java.util.ArrayList;

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
     * Returns the room type name.
     *
     * @return the room type as a String
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Returns the price per night for this room.
     *
     * @return the nightly rate as a double
     */
    public double getPricePerNight() {
        return pricePerNight;
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
        System.out.println("  Room Type   : " + roomType);
        System.out.println("  Beds        : " + numberOfBeds);
        System.out.println("  Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("  Price/Night : $" + pricePerNight);
    }
}

/** Represents a Double Room. */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 140.00, 350.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("  Room Type   : " + roomType);
        System.out.println("  Beds        : " + numberOfBeds);
        System.out.println("  Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("  Price/Night : $" + pricePerNight);
    }
}

/** Represents a Suite Room. */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.00, 750.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("  Room Type   : " + roomType);
        System.out.println("  Beds        : " + numberOfBeds);
        System.out.println("  Size        : " + roomSizeInSqFt + " sq ft");
        System.out.println("  Price/Night : $" + pricePerNight);
    }
}

// ─── Room Inventory ────────────────────────────────────────────────────────

/**
 * RoomInventory manages the availability of all room types
 * using a centralized HashMap as a single source of truth.
 *
 * @author Your Name
 * @version 1.0
 */
class RoomInventory {

    /**
     * Maps each room type name to its current available count.
     */
    private HashMap<String, Integer> availability;

    /**
     * Constructs a RoomInventory and registers all room types
     * with their initial available counts.
     */
    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 0);
        availability.put("Suite Room",  2);
    }

    /**
     * Returns the number of available rooms for the given room type.
     * This is a read-only operation — inventory state is not modified.
     *
     * @param roomType the name of the room type to look up
     * @return the number of available rooms, or 0 if not found
     */
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    /**
     * Returns all room type keys registered in the inventory.
     * Provides read-only access to the set of known room types.
     *
     * @return an iterable set of room type names
     */
    public Iterable<String> getAllRoomTypes() {
        return availability.keySet();
    }

    /**
     * Updates the available count for a given room type.
     *
     * @param roomType the name of the room type to update
     * @param count    the new available count
     */
    public void updateAvailability(String roomType, int count) {
        if (availability.containsKey(roomType)) {
            availability.put(roomType, count);
            System.out.println("  Inventory updated: " + roomType + " → " + count + " available");
        } else {
            System.out.println("  Room type not found: " + roomType);
        }
    }

    /**
     * Displays the current availability of all room types.
     */
    public void displayInventory() {
        System.out.println("----------------------------------------");
        System.out.println("         Current Room Inventory         ");
        System.out.println("----------------------------------------");
        for (String roomType : availability.keySet()) {
            System.out.println("  " + roomType + " : " + availability.get(roomType) + " available");
        }
        System.out.println("----------------------------------------");
    }
}

// ─── Search Service ────────────────────────────────────────────────────────

/**
 * SearchService provides read-only access to room availability
 * and details. It acts as a safe boundary between the guest-facing
 * search flow and the inventory state.
 *
 * <p>Search operations never modify inventory. Room types with zero
 * availability are filtered out before results are returned to the
 * guest, ensuring only actionable options are displayed.</p>
 *
 * @author Your Name
 * @version 1.0
 */
class SearchService {

    /**
     * The inventory instance used for availability lookups.
     * Accessed in read-only mode during all search operations.
     */
    private RoomInventory inventory;

    /**
     * The list of all registered room domain objects.
     * Used to retrieve room details such as pricing and size.
     */
    private ArrayList<Room> roomCatalog;

    /**
     * Constructs a SearchService with access to inventory
     * and the room catalog.
     *
     * @param inventory   the centralized inventory to query
     * @param roomCatalog the list of available room domain objects
     */
    public SearchService(RoomInventory inventory, ArrayList<Room> roomCatalog) {
        this.inventory   = inventory;
        this.roomCatalog = roomCatalog;
    }

    /**
     * Searches for available rooms by checking inventory counts
     * for each room in the catalog.
     *
     * <p>Only rooms with availability greater than zero are displayed.
     * Inventory state is not modified at any point during this operation.</p>
     */
    public void searchAvailableRooms() {
        System.out.println("========================================");
        System.out.println("       BookMyStay - Available Rooms     ");
        System.out.println("========================================");

        // Track whether any available rooms were found
        boolean anyAvailable = false;

        for (Room room : roomCatalog) {

            // Read-only availability check — no state is modified
            int availableCount = inventory.getAvailability(room.getRoomType());

            // Defensive check — filter out rooms with zero availability
            if (availableCount > 0) {
                anyAvailable = true;
                System.out.println();
                room.displayRoomDetails();
                System.out.println("  Available   : " + availableCount + " rooms");
                System.out.println("  ──────────────────────────────────────");
            }
        }

        // Inform guest if no rooms are currently available
        if (!anyAvailable) {
            System.out.println();
            System.out.println("  No rooms are currently available.");
            System.out.println("  Please check back later.");
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("Search completed. No changes were made.");
        System.out.println("========================================");
    }
}

// ─── Application Entry Point ───────────────────────────────────────────────

/**
 * BookMyStayApp serves as the entry point for the BookMyStay application.
 * Demonstrates read-only room search with separation of concerns.
 *
 * @author Your Name
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * Initializes the room catalog, inventory, and search service.
     * Performs a guest-initiated room search without modifying state.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        // ── Build the room catalog ───────────────────────────────────────
        ArrayList<Room> roomCatalog = new ArrayList<>();
        roomCatalog.add(new SingleRoom());
        roomCatalog.add(new DoubleRoom());
        roomCatalog.add(new SuiteRoom());

        // ── Initialize centralized inventory ─────────────────────────────
        // Double Room is intentionally set to 0 to demonstrate filtering
        RoomInventory inventory = new RoomInventory();

        // ── Display inventory before search ──────────────────────────────
        System.out.println();
        inventory.displayInventory();
        System.out.println();

        // ── Guest initiates a room search ────────────────────────────────
        SearchService searchService = new SearchService(inventory, roomCatalog);
        searchService.searchAvailableRooms();

        // ── Confirm inventory is unchanged after search ───────────────────
        System.out.println();
        System.out.println("Verifying inventory is unchanged after search...");
        System.out.println();
        inventory.displayInventory();

        System.out.println();
        System.out.println("Application terminated successfully.");
    }
}
