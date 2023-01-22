package cn.hellp.touch.unitem.item;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public interface BaseBuilder {
    ItemStack toBukkitItemStack();

    void load(YamlConfiguration configuration);

    /**
     * 获取物品的注册名称，也是nbt值
     *
     * @return 物品注册名
     */
    String getName();
}
