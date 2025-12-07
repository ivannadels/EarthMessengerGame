import java.util.ArrayList;
import java.util.List;


//The Player is last human in the game
//This class stores the player's state and the items they carry.
public class Player {

    //What actions the player has done
    private String name;
    private List<Item> inventory;
    private boolean hasListenedToMessage;   // True after using the iPhone
    private boolean hasEaten;               // True after eating the pizza
    private boolean hasDrunkWater;          // True after drinking water
    private static Location currentLocation;

    public Player(String name) {
        inventory = new ArrayList<>();
        this.name = name;
        hasListenedToMessage = false;
        hasEaten = false;
        hasDrunkWater = false;
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
                        .filter(item -> itemToAdd.toString().equalsIgnoreCase(itemToAdd.toString()))
                        .findFirst().orElse(null);
        inventory.add(itemInLocation);
        currentLocation.removeItem(itemInLocation);
        System.out.println(inventory.toString());
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


//if plyaer ate pizza: true
    public boolean hasEaten() {
        return hasEaten;
    }
    //setter for eating pizza
    public void setHasEaten(boolean value) {
        hasEaten = value;
    }


    //if player drank water: true
    public boolean hasDrunkWater() {
        return hasDrunkWater;
    }
    //setter for drinking water
    public void setHasDrunkWater(boolean value) {
        hasDrunkWater = value;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    public Location getCurrentLocation() {
        return this.currentLocation;
    }
}
