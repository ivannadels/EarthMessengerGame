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
 * All locations use the same Room class with different data - no subclasses needed.
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
    private Map<String, Location> connectedRooms;
    private Map<String, String> specialCommands;
    private boolean isOutside;
    private boolean visited;

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
        this.specialCommands = new HashMap<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
    public String getName() {
        return name;
    }
    public String getLongDescription() {
        return longDescription;
    }
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void addSpecialCommand(String input, String command) {
        this.specialCommands.put(input, command);
    }

    public boolean hasSpecialCommand(String command) {
        return this.specialCommands.containsKey(command);
    }

    public List<String> getSpecialCommandDescriptions() {
        return new ArrayList<>(specialCommands.values()); // Return list of descriptions
    }

    public void addConnection(String direction, Location location) {
        connectedRooms.put(direction.toLowerCase(), location);
    }

    public boolean hasConnection(String direction) {
        return connectedRooms.containsKey(direction.toLowerCase())
                && connectedRooms.get(direction.toLowerCase()) != null;
    }

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

