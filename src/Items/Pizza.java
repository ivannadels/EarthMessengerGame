public class Pizza extends Item {

    private boolean eaten = false;

    public Pizza() {
        super("Frozen Pizza", "A preserved pizza for space travel.");
    }

    @Override
    public void use(Player player) {
        if (eaten) {
            System.out.println("The pizza is already gone.");
        } else {
            System.out.println("You eat the pizza and regain some strength.");
            eaten = true;
            player.setHasEaten(true);
        }
    }
}
