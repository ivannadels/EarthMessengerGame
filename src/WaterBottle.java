public class WaterBottle extends Item {

    private boolean empty = false;
    private int thirstScore = 5;

    public WaterBottle() {
        String description = "A bottle of clean drinking water";
        super("watterBottle", description, "\uD83D\uDCA7");
    }
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
