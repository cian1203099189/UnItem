package cn.hellp.touch.unitem.item.style;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Style {

    /**
     * 更新一个样式
     * @param origin 展示的对象
     * @param eventItem 涉及到的物品
     * @return 要更新的String
     */
    String update(Player origin, ItemStack eventItem);
    String now(Player origin);

    /**
     * 获取该样式的命名ID
     * @return 样式ID
     */
    String id();

    static Style create(String raw) {
        if(raw.startsWith("style:")) {
            String styleId = raw.substring(6);
            return StyleManager.get(styleId);
        }
        return new NormalStyle(null,raw);
    }
}
