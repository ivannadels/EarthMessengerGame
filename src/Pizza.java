public class Pizza extends Item {

    private boolean eaten = false;
    private int hungerScore = 5;

    public Pizza() {
        String description = "A frozen meal, ready to eat";
        super("pizza", description, "\uD83C\uDF55");
    }

    @Override
    public void use(Player player) {
        if (eaten) {
            System.out.println("The pizza is already gone.");
        } else {
            System.out.println("You eat the pizza and regain some strength.");
            eaten = true;
            player.setHungerLevel(hungerScore);
        }
    }
}
