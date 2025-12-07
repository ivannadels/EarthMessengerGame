public class Pizza extends Item {

    private boolean eaten = false;
    private int hungerScore = 5;
    private boolean isFrozen;

    public Pizza() {
        String description = "A frozen meal, ready to eat";
        super("pizza", description, "\uD83C\uDF55");
        this.isFrozen = true;
    }

    @Override
    public void use(Player player) {
        if (eaten) {
            System.out.println("The pizza is already gone.");
        } else {
            eaten = true;
            player.setHungerLevel(hungerScore);
        }
    }
    public boolean isFrozen() {
        return isFrozen;
    }
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }
}
