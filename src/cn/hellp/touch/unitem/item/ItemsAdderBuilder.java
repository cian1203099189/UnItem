package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.plugin.Main;
import cn.hellp.touch.unitem.support.ItemsAdderSupport;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderBuilder implements BaseBuilder{
    private String name;
    private String itemsAdderId;

    @Override
    public ItemStack toBukkitItemStack() {
        if (!ItemsAdderSupport.INSTANCE.isLoaded()) {
            throw new ERROR("Can't use itemsAdder item without ItemsAdder plugin!");
        }
        ItemStack itemStack = ItemsAdderSupport.INSTANCE.getCustomStack(itemsAdderId).getItemStack();
        if(name!=null && !name.isEmpty()) {
            itemStack= NbtTool.setString(itemStack, Main.nbtPrefix,name);
        }
        return itemStack;
    }

    @Override
    public void load(YamlConfiguration configuration) {
        name=configuration.getString("name");
        itemsAdderId = configuration.getString("itemsAdderId");
    }

    @Override
    public String getName() {
        return name;
    }
}
