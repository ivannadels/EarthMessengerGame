import java.util.ArrayList;
import java.util.List;


//The Player is last human in the game
//This class stores the player's state and the items they carry.
public class Player {

    //What actions the player has done
    private String name;
    private List<Item> inventory;
    private boolean hasListenedToMessage;   // True after using the iPhone
    private int hungerLevel;
    private int maxHungerLevel;
    private int thirstLevel;
    private int maxThirstLevel;
    private static Location currentLocation;

    public Player(String name) {
        inventory = new ArrayList<>();
        this.name = name;
        hasListenedToMessage = false;
        hungerLevel = 0;
        thirstLevel = 0;
        maxHungerLevel = 5;
        maxThirstLevel = 5;
    }

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

    //players backpack
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


    public void removeItem(CommandParser.Noun itemToRemove) {
        inventory.remove(itemToRemove);
    }

    //list of items plyaer is carrying
    public List<Item> getInventory() {
        return inventory;
    }

    //setter and getters for item missions

    //if player listend: true
    public boolean hasListenedToMessage() {
        return hasListenedToMessage;
    }

    //setter for listening to message
    public void setHasListenedToMessage(boolean value) {
        hasListenedToMessage = value;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }
    //setter for eating food
    public void setHungerLevel(int value) {
        hungerLevel += value;
    }

    public int getThirstLevel() {
        return thirstLevel;
    }
    //setter for drinking water
    public void setThirstLevel(int value) {
        thirstLevel += value;
    }

    public Item getItemFromInventory(String itemName) {
        return inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }


    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    public Location getCurrentLocation() {
        return this.currentLocation;
    }
}
