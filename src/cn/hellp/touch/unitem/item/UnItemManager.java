package cn.hellp.touch.unitem.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnItemManager {
    private static final Map<String,UnItem> unItems = new HashMap<>();

    public static void clear() {
        unItems.clear();
    }

    public static UnItem getItem(String name) {
        return unItems.get(name);
    }

    public static String asString() {
        StringBuilder builder = new StringBuilder();
        for (String s : unItems.keySet()) {
            builder.append(s);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static List<String> itemNames() {
        return new ArrayList<>(unItems.keySet());
    }

    public static void addItem(UnItem unItem) {
        unItems.put(unItem.getName(),unItem);
    }

    public static boolean contains(String name) {
        if(name==null) {
            return false;
        }
        return unItems.containsKey(name);
    }

    public static void removeItem(String name) {
        unItems.remove(name);
    }
}
