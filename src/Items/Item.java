public abstract class Item {

    protected String name;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // هر آیتم رفتار خاص خودش رو داره
    public abstract void use(Player player);

    @Override
    public String toString() {
        return
        public abstract class Item {

            protected String name;
            protected String description;

            public Item(String name, String description) {
                this.name = name;
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            //each item is being used in its specific way، it it the
            //behavior when the player uses item
            public abstract void use(Player player);

            @Override
            public String toString() {
                return name;
            }
        }
