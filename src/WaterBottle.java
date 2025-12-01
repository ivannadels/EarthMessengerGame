public class WaterBottle extends Item {

    private boolean empty = false;

    public WaterBottle() {
        super("Water Bottle", "Purified ship water.");
    }

    @Override
    public void use(Player player) {
        if (empty) {
            System.out.println("The bottle is empty.");
        } else {
            System.out.println("You drink the water.");
            empty = true;
            player.setHasDrunkWater(true);
        }
    }
}
