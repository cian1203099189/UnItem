package cn.hellp.touch.unitem.support;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import dev.lone.itemsadder.api.NotActuallyItemsAdderException;

public enum ItemsAdderSupport {
    INSTANCE;
    private boolean loaded;

    public boolean init() {
        try {
            ItemsAdder.getAllItems();
            return loaded = true;
        } catch (NotActuallyItemsAdderException e) {
            return loaded = false;
        }
    }

    public CustomStack getCustomStack(String id) {
        return CustomStack.getInstance(id);
    }

    public boolean isLoaded() {
        return loaded;
    }
}
