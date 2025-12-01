public class IPhone extends Item {

    public IPhone() {
        super("iPhone 16 Pro", "A device containing a mission message from Earth.");
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
