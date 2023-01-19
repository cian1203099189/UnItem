package cn.hellp.touch.unitem.item;

public enum BuilderAttribute {
    NORMAL {
        @Override
        public BaseBuilder createBuilder() {
            return new ItemBuilder();
        }
    },
    ITEMSADDER {
        @Override
        public BaseBuilder createBuilder() {
            return new ItemsAdderBuilder();
        }
    };

    public static BuilderAttribute getFromName(String name) {
        return valueOf(name.toUpperCase());
    }

    public abstract BaseBuilder createBuilder();
}
