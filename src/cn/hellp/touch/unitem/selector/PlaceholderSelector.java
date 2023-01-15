package cn.hellp.touch.unitem.selector;

import cn.hellp.touch.unitem.app.PlaceholderManager;
import org.bukkit.entity.Player;

public class PlaceholderSelector implements ISelector{
    public final String name;
    public final PlaceholderManager manager;

    public PlaceholderSelector(String name,PlaceholderManager manager) {
        this.name=name;
        this.manager=manager;
    }

    @Override
    public Object[] select(Player invoker) {
        return manager.get(name).select(invoker);
    }

    @Override
    public String selectorID() {
        return "placeholder";
    }
}
