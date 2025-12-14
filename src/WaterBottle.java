/**
 * Represents a water bottle item in the game.
 * Drinking from the water bottle reduces the player's thirst level.
 */
public class WaterBottle extends Item {

    private boolean empty = false;
    private int thirstScore = 5;

    /**
     * Constructs a new WaterBottle item with a description and graphic.
     * Initially, the bottle is full and can be used to quench thirst.
     */
    public WaterBottle() {
        String description = "A bottle of clean drinking water";
        super("watterBottle", description, "\uD83D\uDCA7");
    }

    /**
     * Uses the water bottle.
     * @param player The player who is drinking the water
     */
    @Override
    public void use(Player player) {
        if (empty) {
            System.out.println("The bottle is empty.");
        } else {
            System.out.println("You drink the water.");
            empty = true;
            player.setThirstLevel(thirstScore);
        }
    }
}
