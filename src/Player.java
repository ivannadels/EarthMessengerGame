import java.util.ArrayList;
import java.util.List;


/**
 * Represents the player in the Earth Messenger game.
 * The player is the last human alive, carrying items, managing hunger/thirst,
 * and interacting with the environment.
 */
public class Player {

    private String name;
    private List<Item> inventory;
    private boolean hasListenedToMessage;   // True after using the iPhone
    private int hungerLevel;
    private int maxHungerLevel;
    private int thirstLevel;
    private int maxThirstLevel;
    private static Location currentLocation;

    /**
     * Constructs a new player with the given name.
     * Initializes inventory and sets hunger/thirst levels to zero.
     *
     * @param name The player's name
     */
    public Player(String name) {
        inventory = new ArrayList<>();
        this.name = name;
        hasListenedToMessage = false;
        hungerLevel = 0;
        thirstLevel = 0;
        maxHungerLevel = 5;
        maxThirstLevel = 5;
    }

    /**
     * Uses an item from the player's inventory.
     *
     * @param itemToUse The noun representing the item to use
     */
    public void useItem(CommandParser.Noun itemToUse) {
        Item itemInInventory = inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemToUse.toString()))
                .findFirst().orElse(null);
        if(inventory.contains(itemInInventory)) {
            itemInInventory.use(this);
        }
        else{
            System.out.println("This item is not in your inventory.");
        }
    }

    /**
     * Adds an item from the current location to the player's inventory.
     *
     * @param itemToAdd The noun representing the item to add
     */
    public void addItem(CommandParser.Noun itemToAdd) {
        Item itemInLocation = currentLocation.getItems().stream()
                .filter(item -> itemToAdd.matchesItem(item))
                .findFirst()
                .orElse(null);

        if (itemInLocation != null) {
            inventory.add(itemInLocation);
            currentLocation.removeItem(itemInLocation);
            System.out.println("Added: " + itemInLocation.getName());
        } else {
            System.out.println("No such item found in location.");
        }
    }

    /**
     * Removes an item from the player's inventory.
     *
     * @param itemToRemove The noun representing the item to remove
     */
    public void removeItem(CommandParser.Noun itemToRemove) {
        inventory.remove(itemToRemove);
    }

    /**
     * Gets the list of items in the player's inventory.
     *
     * @return The inventory list
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Checks if the player has listened to the mission message.
     *
     * @return true if the player has listened, false otherwise
     */
    public boolean hasListenedToMessage() {
        return hasListenedToMessage;
    }

    /**
     * Sets whether the player has listened to the mission message.
     *
     * @param value true if listened, false otherwise
     */
    public void setHasListenedToMessage(boolean value) {
        hasListenedToMessage = value;
    }

    /**
     * Gets the player's current hunger level.
     *
     * @return The hunger level
     */
    public int getHungerLevel() {
        return hungerLevel;
    }

    /**
     * Increases the player's hunger level by the given value.
     *
     * @param value The amount to increase hunger
     */
    public void setHungerLevel(int value) {
        hungerLevel += value;
    }

    /**
     * Gets the player's current thirst level.
     *
     * @return The thirst level
     */
    public int getThirstLevel() {
        return thirstLevel;
    }

    /**
     * Increases the player's thirst level by the given value.
     *
     * @param value The amount to increase thirst
     */
    public void setThirstLevel(int value) {
        thirstLevel += value;
    }

    /**
     * Retrieves an item from the player's inventory by name.
     *
     * @param itemName The name of the item
     * @return The item if found, otherwise null
     */
    public Item getItemFromInventory(String itemName) {
        return inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Sets the player's current location.
     *
     * @param currentLocation The location to set
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    /**
     * Gets the player's current location.
     *
     * @return The current location
     */
    public Location getCurrentLocation() {
        return this.currentLocation;
    }
}
