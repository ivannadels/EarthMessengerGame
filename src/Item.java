/**
 * Represents an interactive item within the game world.
 *
 * Each item has a name, a description, and an optional graphic symbol.
 * Items may be picked up, stored in the player's inventory, and used to
 * trigger specific behaviors or effects. Subclasses define the concrete
 * behavior of the {@link #use(Player)} method.
 */
public abstract class Item {

    protected String name;
    protected String description;
    protected String graphic;
    protected boolean isUsed;

    /**
     * Constructs a new item with the given properties.
     *
     * @param name        the display name of the item
     * @param description a short description of the item
     * @param graphic     a visual symbol or emoji representing the item
     */
    public Item(String name, String description, String graphic) {
        this.name = name;
        this.description = description;
        this.graphic = graphic;
        this.isUsed = false;
    }

    /**
     * Returns the name of this item.
     *
     * @return the item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this item.
     *
     * @return the item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the graphic symbol associated with this item.
     *
     * @return the item's graphic symbol
     */
    public String getGraphic() {
        return graphic;
    }

    /**
     * Checks whether this item has already been used.
     *
     * @return true if the item has been used, false otherwise
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Marks this item as used or unused.
     *
     * @param isUsed true if the item has been used, false otherwise
     */
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    /**
     * Defines the behavior that occurs when the player uses this item.
     *
     * Each subclass provides its own implementation depending on the
     * item's purpose and effect within the game.
     *
     * @param player the player using the item
     */
    public abstract void use(Player player);
}

