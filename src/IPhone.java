public class IPhone extends Item {

    public IPhone() {
        String description = "A sleek smartphone with a recorded message";
        super("iphone", description, "\uD83D\uDCF1");
    }

    @Override
    public void use(Player player) {
        if (player.hasListenedToMessage()) {
            System.out.println("You already listened to the mission message.");
        } else {
            System.out.println("Playing the mission briefing...");
            System.out.println("\"You are the last human. Your mission is to prove humanity's worth.\"");
            player.setHasListenedToMessage(true);
        }
    }
}
