public class IPhone extends Item {

    public IPhone() {
        String description = "A sleek smartphone with a recorded message";
        super("iphone", description, "\uD83D\uDCF1");
    }

    @Override
    public void use(Player player) {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("           📱 MISSION BRIEFING - PLAYING...           ");
        System.out.println("═══════════════════════════════════════════════════════\n");

        pause(1000);

        System.out.println("\n\"If you're hearing this... you made it. You're awake.\"");
        pause(2000);

        System.out.println("\n\"Earth is gone. The evils of greed and corruption led");
        System.out.println("us to destruction. Everyone is gone... but we managed");
        System.out.println("to freeze you in time. You were chosen for your mental");
        System.out.println("ability and youth. You had the best chance.\"");
        pause(2500);

        System.out.println("\n\"The scientists at SpaceCorp have been studying Planet");
        System.out.println("B360 for many years. Our studies showed this planet has");
        System.out.println("all the right elements for potential human survival.");
        System.out.println("We have sent you there as humanity's last hope.\"");
        pause(2500);

        System.out.println("\n\"But there's something important you must know...\"");
        pause(2000);

        System.out.println("\n\"The planet isn't empty. There's a species there...");
        System.out.println("They are extremely intelligent and advanced.");
        System.out.println("They've been studying us through our ruins and broadcasts for decades.");
        System.out.println("They know what we were.");
        pause(2500);

        System.out.println("\n\"Your mission is simple: convince them you're human.");
        System.out.println("Convince them your intentions are pure. Convince them");
        System.out.println("to let humanity, what's left of it, begin again.\"");
        pause(2000);

        System.out.println("\n\"You are Earth's messenger. You are our last chance.\"");
        pause(2000);

        System.out.println("\n\"Good luck.\"");
        pause(1500);

        System.out.println("\n═══════════════════════════════════════════════════════\n");

        player.setHasListenedToMessage(true);
    }

    /**
     * Pauses for dramatic effect during message.
     * @param milliseconds Time to pause
     */
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
