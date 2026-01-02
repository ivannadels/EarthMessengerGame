/**
 * Represents a pizza item in the game.
 * The pizza starts frozen and must be microwaved before it can be eaten.
 * Eating the pizza reduces the player's hunger level.
 */
public class Pizza extends Item {

    private boolean eaten = false;
    private int hungerScore = 5;
    private boolean isFrozen;

    /**
     * Constructs a new Pizza item with a description and graphic.
     * By default, the pizza starts frozen.
     */
    public Pizza() {
        String description = "A frozen meal, ready to eat";
        super("pizza", description, "\uD83C\uDF55");
        this.isFrozen = true;
    }

    /**
     * Uses the pizza item.
     * @param player The player who is eating the pizza
     */
    @Override
    public void use(Player player) {
        if (eaten) {
            System.out.println("The pizza is already gone.");
        } else {
            eaten = true;
            player.setHungerLevel(hungerScore);
        }
    }

    /**
     * Checks whether the pizza is currently frozen.
     *
     * @return true if the pizza is frozen, false otherwise
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Sets the frozen state of the pizza.
     *
     * @param frozen true if the pizza should be frozen, false if microwaved
     */
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }
}