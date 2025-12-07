        public abstract class Item {

            protected String name;
            protected String description;
            protected boolean isUsed;

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

            public boolean isUsed() {
                return isUsed;
            }

            public void setUsed(boolean isUsed) {
                this.isUsed = isUsed;
            }

            //each item is being used in its specific way، it it the
            //behavior when the player uses item
            public abstract void use(Player player);

            @Override
            public String toString() {
                return name;
            }
        }
