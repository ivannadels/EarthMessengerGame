import java.util.ArrayList;
import java.util.List;

//The Player is last human in the game
//This class stores the player's state and the items they carry.
public class Player {

    //What actions the player has done
    private boolean hasListenedToMessage;   // True after using the iPhone
    private boolean hasEaten;               // True after eating the pizza
    private boolean hasDrunkWater;          // True after drinking water

    // The items that the player is currently carrying.
    private List<Item> inventory = new ArrayList<>();

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


//players backpack
    public void addItem(Item item) {
        inventory.add(item);
    }
    //list of items plyaer is carrying
    public List<Item> getInventory() {
        return inventory;
    }
}
