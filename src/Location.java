/**
 * Represents a location in the game world that the player can visit.
 *
 * Rooms contain descriptions, connections to other rooms (via directional commands
 * like north/south/east/west or action commands like enter/exit), items that can
 * be picked up, and potentially an alien occupant.
 *
 * Rooms track whether they've been visited and whether they're outside locations
 * (like Planet Surface) or indoor locations (like chambers and spaceship).
 *
 * All locations use the same location class with different data - no subclasses needed.
 *
 * Example usage:
 *   Room spaceship = new Room("Spaceship", "A small one-person spaceship...", false);
 *   spaceship.addItem(iphone);
 *   spaceship.addConnection("exit", planetSurface);
 *   String desc = spaceship.getDescription();
 */
import java.util.*;
public class Location {

    private String name;
    private String longDescription;
    private String shortDescription;
    private List<Item> items;
    private List<Item> availableItems;
    private Map<String, Location> connectedRooms;
    private Map<String, String> specialCommands;
    private boolean isOutside;
    // is the player inside yet
    private boolean playerEntered;
    // has the player already visited the location
    private boolean visited;
    // has the player searched the location thoroughly (spaceship-specific)
    private boolean hasBeenSearched;
    // has the player passed the chambers test
    private boolean hasBeenPassed;
    // has the player completed all the required tasks of the location
    private boolean hasBeenCompleted;

    /**
     * Constructs a new location with the given name and type.
     * Initializes directional connections (north, south, east, west) to null.
     *
     * @param isOutside True if the location is outdoors, false if indoors
     * @param name The name of the location
     */
    public Location(boolean isOutside, String name) {
        this.name = name;
        this.isOutside = isOutside;
        this.connectedRooms = new HashMap<>();
            addConnection("north", null);
            addConnection("south", null);
            addConnection("east", null);
            addConnection("west", null);
        this.items = new ArrayList<>();
        this.visited = false;
        this.hasBeenSearched = false;
        this.specialCommands = new HashMap<>();
        this.availableItems = new  ArrayList<>();
        this.playerEntered = false;
    }

    /**
     * Adds an item to the location.
     *
     * @param item The item to add
     */
    public void addItem(Item item, boolean isAvailable) {
        items.add(item);
        if(isAvailable) {
            availableItems.add(item);
        }
    }

    /**
     * Removes an item from the location.
     *
     * @param item The item to remove
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gets the list of items currently in the location.
     *
     * @return A list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Checks if the specified item is available or not
     *
     * @return boolean
     */
    public boolean isItemAvailable(String itemName) {
        for(Item item : items) {
            if(item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the room has been fully searched or not
     *
     * @return boolean
     */
    public boolean hasBeenSearched() {
        return hasBeenSearched;
    }

    public void setSearched(boolean searched) {
        hasBeenSearched = searched;
    }

    public boolean hasBeenCompleted() {
        return hasBeenCompleted;
    }
    public void setCompleted(boolean completed) {
        hasBeenCompleted = completed;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public boolean hasPlayerEntered() {
        return playerEntered;
    }
    public void setPlayerEntered(boolean playerEntered) {
        this.playerEntered = playerEntered;
    }

    public boolean hasBeenPassed() {
        return hasBeenPassed;
    }
    public void setHasBeenPassed(boolean hasBeenPassed) {
        this.hasBeenPassed = hasBeenPassed;
    }
    /**
     * Gets the name of the location.
     *
     * @return The location name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the long description of the location.
     *
     * @return The long description
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the long description of the location.
     *
     * @param longDescription The detailed description
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * Gets the short description of the location.
     *
     * @return The short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the short description of the location.
     *
     * @param shortDescription The brief description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Adds a special command unique to this location.
     *
     * @param input The command input string
     * @param command The mapped action for the command
     */
    public void addSpecialCommand(String input, String command) {
        this.specialCommands.put(input, command);
    }

    /**
     * Checks if the location supports a given special command.
     *
     * @param command The command string
     * @return true if the command exists, false otherwise
     */
    public boolean hasSpecialCommand(String command) {
        return this.specialCommands.containsKey(command);
    }

    /**
     * Gets the list of special command descriptions for this location.
     *
     * @return A list of special command descriptions
     */
    public List<String> getSpecialCommandDescriptions() {
        return new ArrayList<>(specialCommands.values()); // Return list of descriptions
    }

    /**
     * Adds a connection from this location to another.
     *
     * @param direction The direction or action command (e.g., "north", "exit")
     * @param location The connected location
     */
    public void addConnection(String direction, Location location) {
        connectedRooms.put(direction.toLowerCase(), location);
    }

    /**
     * Checks if a connection exists in the given direction.
     *
     * @param direction The direction to check
     * @return true if a connected room exists, false otherwise
     */
    public boolean hasConnection(String direction) {
        return connectedRooms.containsKey(direction.toLowerCase())
                && connectedRooms.get(direction.toLowerCase()) != null;
    }

    /**
     * Gets the connected room in the given direction.
     *
     * @param direction The direction to move
     * @return The connected location, or null if none exists
     */
    public Location getConnectedRoom(String direction) {
        return connectedRooms.get(direction.toLowerCase());
    }

    /**
     * define a location for aliens
     */
    private Alien roomOccupant;

    public void addOccupant(Alien alien){
        this.roomOccupant = alien;
    }
    public Alien getOccupant() {
        return roomOccupant;
    }
    public boolean hasOccupant(){
        return roomOccupant != null;
    }
}

