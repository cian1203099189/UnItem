package cn.hellp.touch.unitem.item.style;

import cn.hellp.touch.unitem.support.PlaceholderAPISupport;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NormalStyle implements Style {
    protected final String value;
    protected final @Nullable String id;

    public NormalStyle(@Nullable String id, String value) {
        this.id=id;
        this.value = value;
    }

    @Override
    public String update(Player origin, ItemStack eventItem) {
        if(PlaceholderAPISupport.INSTANCE.isLoaded() && origin!=null)
            return PlaceholderAPISupport.INSTANCE.parse(origin,value);
        return value;
    }

    @Override
    public String now(Player origin) {
        return update(origin,null);
    }

    @Override
    public String id() {
        return id==null ? "string" : id;
    }
}
