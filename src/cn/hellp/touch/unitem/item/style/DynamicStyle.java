package cn.hellp.touch.unitem.item.style;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DynamicStyle implements Style {
    protected final String id;
    protected final List<Style> styleList = new ArrayList<>();
    protected final Map<UUID, Integer> nowIndexMap = new HashMap<>();

    public DynamicStyle(String id) {
        this.id = id;
    }

    public void addStyle(Style style) {
        styleList.add(style);
    }

    @Override
    public String update(Player origin, ItemStack eventItem) {
        if (styleList.isEmpty()) {
            return null;
        }
        if (origin == null) {
            return styleList.get(0).update(null, eventItem);
        }
        int index = nowIndexMap.getOrDefault(origin.getUniqueId(), nowIndexMap.putIfAbsent(origin.getUniqueId(), 0));
        if (index >= styleList.size()) {
            nowIndexMap.replace(origin.getUniqueId(), 0);
            index = 0;
        }
        String s = styleList.get(index++).update(origin, eventItem);
        nowIndexMap.replace(origin.getUniqueId(), index);
        return s;
    }

    @Override
    public String now(Player origin) {
        if (styleList.isEmpty()) {
            return null;
        }
        if (origin == null) {
            return styleList.get(0).update(null,null);
        }
        int index = nowIndexMap.getOrDefault(origin.getUniqueId(), nowIndexMap.putIfAbsent(origin.getUniqueId(), 0));
        if (index >= styleList.size()) {
            nowIndexMap.replace(origin.getUniqueId(), 0);
            index = 0;
        }
        return styleList.get(index).now(origin);
    }

    @Override
    public String id() {
        return id;
    }
}
