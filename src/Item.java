        public abstract class Item {

            protected String name;
            protected String description;
            protected String graphic;
            protected boolean isUsed;

            public Item(String name, String description,  String graphic) {
                this.name = name;
                this.description = description;
                this.graphic = graphic;
                this.isUsed = false;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }
            public String getGraphic() {
                return graphic;
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
